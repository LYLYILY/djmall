package com.dj.mall.product.impl.product;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.constant.ProductSkuConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.dto.product.ProductDTO;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import com.dj.mall.product.entity.product.ProductSkuEntity;
import com.dj.mall.product.es.ProductIndex;
import com.dj.mall.product.mapper.product.ProductSkuMapper;
import com.dj.mall.product.mapper.bo.ProductSkuBO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSkuEntity> implements ProductSkuService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 新增productsku
     *
     * @param productDTO
     * @throws Exception
     */
    @Override
    public void addSkuAll(ProductDTO productDTO) throws Exception {
        List<ProductSkuDTO> skuList = new ArrayList<>();
        for (ProductSkuDTO productSkuDTO : productDTO.getSkuList()) {
            if (productSkuDTO.getSkuName() == null || productSkuDTO.getSkuName() == ""){
                continue;
            }
            productSkuDTO.setProductId(productDTO.getId());
            productSkuDTO.setBusinessId(productDTO.getBusinessId());
            productSkuDTO.setUpdateTime(new Date());
            skuList.add(productSkuDTO);
        }
        super.saveBatch(DozerUtil.mapList(skuList, ProductSkuEntity.class));
    }

    /**
     * 修改上下架
     *
     * @param productStatus
     * @param id
     */
    @Override
    public void updateSkuStatus(String productStatus, Integer id) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("product_id", id);
        updateWrapper.set("sku_status", productStatus);
        super.update(updateWrapper);
    }

    /**
     * 商品修改sku列表展示
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSkuDTO> findSkuByProductId(Integer id) throws Exception {
        QueryWrapper<ProductSkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", id);
        queryWrapper.eq("sku_status", ProductSkuConstant.UP);
        List<ProductSkuEntity> list=super.list(queryWrapper);
        return DozerUtil.mapList(list,ProductSkuDTO.class);
    }

    /**
     * 修改库存回显
     *
     * @param id
     * @return
     */
    @Override
    public ProductSkuDTO findSkuById(Integer id) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        ProductSkuEntity productList = super.getOne(queryWrapper);
        return DozerUtil.map(productList, ProductSkuDTO.class);
    }

    /**
     * 修改库存保存
     *
     * @param productSkuDTO
     * @throws Exception
     */
    @Override
    public void updateSkuCount(ProductSkuDTO productSkuDTO) throws Exception {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("sku_count", productSkuDTO.getSkuCount());
        updateWrapper.eq("id", productSkuDTO.getId());
        super.update(updateWrapper);
    }

    /**
     * 编辑保存
     *
     * @param productSkuDTO
     * @throws Exception
     */
    @Override
    public void updateSku(ProductSkuDTO productSkuDTO) throws Exception {
        super.updateById(DozerUtil.map(productSkuDTO, ProductSkuEntity.class));
    }

    /**
     * 修改默认
     *
     * @param productSkuDTO
     * @throws Exception
     */
    @Override
    public void updateDefault(ProductSkuDTO productSkuDTO) throws Exception, BusinessException {
        QueryWrapper<ProductSkuEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", productSkuDTO.getId());
        ProductSkuEntity one = super.getOne(queryWrapper);
        if (one.getIsDefault().equals(ProductSkuConstant.IS_DEFAULT)) {
            throw new BusinessException("该sku已是默认");
        }
        QueryWrapper<ProductSkuEntity> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("product_id", productSkuDTO.getProductId());
        queryWrapper1.eq("is_default", ProductSkuConstant.IS_DEFAULT);
        ProductSkuEntity one1 = super.getOne(queryWrapper1);
        one1.setIsDefault(ProductSkuConstant.NO_DEFAULT);
        super.updateById(one1);
        productSkuDTO.setIsDefault(ProductSkuConstant.IS_DEFAULT);
        super.updateById(DozerUtil.map(productSkuDTO, ProductSkuEntity.class));
    }

    /**
     * 修改下架
     *
     * @param productSkuDTO
     * @throws Exception
     */
    @Override
    public void downShelf(ProductSkuDTO productSkuDTO) throws Exception {
        super.updateById(DozerUtil.map(productSkuDTO, ProductSkuEntity.class));
    }

    /**
     * 商城首页展示
     *
     * @param pageNo
     * @param productDTO
     * @return
     */
    @Override
    public PageResult findProductSkuAll(Integer pageNo, ProductDTO productDTO) throws Exception {
        /*IPage<ProductSkuBO> iPage = super.baseMapper.findProductSkuAll(new Page<>(pageNo, 3), DozerUtil.map(productSkuDTO, ProductSkuBO.class));
        return PageResult.pageInfo(iPage.getCurrent(),iPage.getPages(), iPage.getRecords());*/
        //es获取
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (productDTO == null && StringUtils.isEmpty(productDTO)) {
            searchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            if (null != productDTO.getProductName() && !productDTO.getProductName().isEmpty()) {
                boolQueryBuilder.must(QueryBuilders.multiMatchQuery(productDTO.getProductName(), "product_name", "product_disc"));
            }
            if (null != productDTO.getSkuPriceMin() || null != productDTO.getSkuPriceMax()) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("sku_price").from(productDTO.getSkuPriceMin()).to(productDTO.getSkuPriceMax()));
            }
            if (null != productDTO.getProductTypeList() && !productDTO.getProductTypeList().isEmpty()) {
                BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
                productDTO.getProductTypeList().forEach(aa -> {
                    boolQueryBuilder1.should(QueryBuilders.multiMatchQuery(aa, "product_type"));
                });
                boolQueryBuilder.must(boolQueryBuilder1);
            }
            searchQueryBuilder.withQuery(boolQueryBuilder);
        }
        // 不加下面默认第一页 10条
        // 分页设置 页码,每页条数 注意 es的page是从0开始 且 page+size的值默认不能大于10000
        searchQueryBuilder.withPageable(PageRequest.of(pageNo-1, 5));
        // 查询
        SearchHits<ProductIndex> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), ProductIndex.class);
        List<ProductIndex> productIndexList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        // 总条数
        double totalHits = searchHits.getTotalHits();
        long l = new Double(Math.ceil(totalHits / 5)).longValue();
        return PageResult.pageInfo(pageNo.longValue(), l, DozerUtil.mapList(productIndexList, ProductDTO.class));
    }

    /**
     * 订单详情-选择商品信息
     *
     * @param productId
     * @return
     */
    @Override
    public List<ProductSkuDTO> findSkuNameById(Integer productId) throws Exception {
        QueryWrapper<ProductSkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        List<ProductSkuEntity> list = super.list(queryWrapper);
        return DozerUtil.mapList(list, ProductSkuDTO.class);
    }

    /**
     * 根据sku属性查找数据
     *
     * @param id
     * @return
     */
    @Override
    public ProductSkuDTO findProductById(Integer id) throws Exception {
        ProductSkuBO productSkuBO = super.baseMapper.findProductBySkuName(id);
        return DozerUtil.map(productSkuBO, ProductSkuDTO.class);
    }

    /**
     * 根据ids修改库存
     * @param list
     */
    @Override
    public void updateSkuCountByIds(List<ProductSkuDTO> list) {
        super.updateBatchById(DozerUtil.mapList(list, ProductSkuEntity.class));
    }

    /**
     * 根据ids查询商品属性
     *
     * @param ids
     * @return
     */
    @Override
    public List<ProductSkuDTO> findProductSkuByIds(List<Integer> ids) throws Exception {
        List<ProductSkuEntity> list = (List<ProductSkuEntity>) super.listByIds(ids);
        return DozerUtil.mapList(list, ProductSkuDTO.class);
    }


}
