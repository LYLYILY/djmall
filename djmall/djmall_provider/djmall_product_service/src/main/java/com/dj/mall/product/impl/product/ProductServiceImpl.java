package com.dj.mall.product.impl.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.constant.ProductSkuConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.product.ProductService;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.dto.product.ProductDTO;
import com.dj.mall.product.entity.product.ProductEntity;
import com.dj.mall.product.es.ProductIndex;
import com.dj.mall.product.mapper.product.ProductMapper;
import com.dj.mall.product.mapper.bo.ProductBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {

    @Reference
    private ProductSkuService productSkuService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 添加商品和商品sku
     *
     * @param productDTO
     * @throws Exception
     */
    @Override
    public void addProductSku(ProductDTO productDTO) throws Exception {
        if (productDTO.getSkuList().size() == ProductSkuConstant.ONE){
            throw new BusinessException("商品sku不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_name",productDTO.getProductName());
        ProductEntity productEntity = super.getOne(queryWrapper);
        if (productEntity != null){
            throw new BusinessException("名称不能重复");
        }
        super.save(DozerUtil.map(productDTO, ProductEntity.class));
        ProductEntity product = super.getOne(queryWrapper);
        productDTO.setId(product.getId());
        productSkuService.addSkuAll(productDTO);
        //存es
        ProductBO productBO = super.baseMapper.findProductEs(product.getId());
        ProductIndex productIndex = ProductIndex.builder()
                .productId(String.valueOf(productBO.getId()))
                .productName(productBO.getProductName())
                .productType(productBO.getProductType())
                .productTypeShow(productBO.getProductTypeShow())
                .freight(productBO.getFreight())
                .productImg(productBO.getProductImg())
                .productDisc(productBO.getProductDisc())
                .likeNum(productBO.getLikeNum())
                .skuPrice(productBO.getSkuPrice())
                .skuCount(productBO.getSkuCount())
                .skuRate(productBO.getSkuRate())
                .build();
        elasticsearchRestTemplate.save(productIndex);
    }

    /**
     * 商品展示
     *
     * @param pageNo
     * @param productDTO
     * @return
     */
    @Override
    public PageResult findProductAll(Integer pageNo, ProductDTO productDTO) throws Exception {
        IPage<ProductBO> iPage = super.baseMapper.findProductAll(new Page<>(pageNo, 5), DozerUtil.map(productDTO, ProductBO.class));
        return PageResult.pageInfo(iPage.getCurrent(),iPage.getPages(), iPage.getRecords());
    }

    /**
     * 修改上下架
     *
     * @param productDTO
     */
    @Override
    public void updateStatus(ProductDTO productDTO) throws Exception {
        super.updateById(DozerUtil.map(productDTO, ProductEntity.class));
        productSkuService.updateSkuStatus(productDTO.getProductStatus(), productDTO.getId());
    }

    /**
     * 修改回显
     *
     * @param id
     * @return
     */
    @Override
    public ProductDTO findProductById(Integer id) throws Exception {
        ProductEntity product = super.getById(id);
        return DozerUtil.map(product, ProductDTO.class);
    }

    /**
     * 修改商品保存
     *
     * @param productDTO
     * @throws Exception
     */
    @Override
    public void update(ProductDTO productDTO) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_name",productDTO.getProductName());
        ProductEntity productEntity = super.getOne(queryWrapper);
        if (productEntity != null && !productDTO.getId().equals(productEntity.getId())){
            throw new BusinessException("名称不能重复");
        }
        super.updateById(DozerUtil.map(productDTO, ProductEntity.class));
    }
}
