package com.dj.mall.product.api.product;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.product.dto.product.ProductDTO;
import com.dj.mall.product.dto.product.ProductSkuDTO;

import java.util.List;

public interface ProductSkuService {

    /**
     * 新增productsku
     * @param productDTO
     * @throws Exception
     */
    void addSkuAll(ProductDTO productDTO) throws Exception;

    /**
     * 修改上下架
     * @param productStatus
     * @param id
     */
    void updateSkuStatus(String productStatus, Integer id) throws Exception;

    /**
     * 商品修改sku列表展示
     * @param id
     * @return
     * @throws Exception
     */
    List<ProductSkuDTO> findSkuByProductId(Integer id) throws Exception;

    /**
     * 修改库存回显
     * @param id
     * @return
     */
    ProductSkuDTO findSkuById(Integer id) throws Exception;

    /**
     * 修改库存保存
     * @param productSkuDTO
     * @throws Exception
     */
    void updateSkuCount(ProductSkuDTO productSkuDTO) throws Exception;

    /**
     * 编辑保存
     * @param productSkuDTO
     * @throws Exception
     */
    void updateSku(ProductSkuDTO productSkuDTO) throws Exception;

    /**
     * 修改默认
     * @param productSkuDTO
     * @throws Exception
     */
    void updateDefault(ProductSkuDTO productSkuDTO) throws Exception, BusinessException;

    /**
     * 修改下架
     * @param productSkuDTO
     * @throws Exception
     */
    void downShelf(ProductSkuDTO productSkuDTO) throws Exception;

    /**
     * 商城首页展示
     * @param pageNo
     * @param productDTO
     * @return
     */
    PageResult findProductSkuAll(Integer pageNo, ProductDTO productDTO) throws Exception;
   /* List<ProductDTO> findProductSkuAll(Integer pageNo, ProductDTO productDTO) throws Exception;*/

    /**
     * 订单详情-选择商品信息
     * @param productId
     * @return
     */
    List<ProductSkuDTO> findSkuNameById(Integer productId) throws Exception;

    /**
     * 根据sku属性查找数据
     * @param id
     * @return
     */
    ProductSkuDTO findProductById(Integer id) throws Exception;


    /**
     * 根据ids查询商品sku
     * @param ids
     * @return
     */
    List<ProductSkuDTO> findProductSkuByIds(List<Integer> ids) throws Exception;

    /**
     * 根据ids修改库存
     * @param list
     */
    void updateSkuCountByIds(List<ProductSkuDTO> list);
}
