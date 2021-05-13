package com.dj.mall.product.api.product;

import com.dj.mall.common.base.PageResult;
import com.dj.mall.product.dto.product.ProductDTO;

public interface ProductService {

    /**
     * 添加商品和商品sku
     * @param productDTO
     * @throws Exception
     */
    void addProductSku(ProductDTO productDTO) throws Exception;

    /**
     * 商品展示
     * @param pageNo
     * @param productDTO
     * @return
     */
    PageResult findProductAll(Integer pageNo, ProductDTO productDTO) throws Exception;

    /**
     * 修改上下架
     * @param productDTO
     */
    void updateStatus(ProductDTO productDTO) throws Exception;

    /**
     * 修改回显
     * @param id
     * @return
     */
    ProductDTO findProductById(Integer id) throws Exception;

    /**
     * 修改商品保存
     * @param productDTO
     * @throws Exception
     */
    void update(ProductDTO productDTO) throws Exception;
}
