package com.dj.mall.product.api.shop;

import com.dj.mall.product.dto.product.ProductSkuDTO;
import com.dj.mall.product.dto.shop.ShopCarDTO;

import java.util.List;

public interface ShopCarService {

    /**
     * 添加至购物车
     * @param shopCarDTO
     * @throws Exception
     */
    void addShopCar(ShopCarDTO shopCarDTO) throws Exception;

    /**
     * 购物车展示
     * @param userId
     * @param buyStatus
     * @return
     */
    List<ShopCarDTO> show(Integer userId, Integer buyStatus) throws Exception;

    /**
     * 查询数量
     * @param shopCarDTO
     * @return
     * @throws Exception
     */
    ProductSkuDTO checkBuyNum(ShopCarDTO shopCarDTO) throws Exception;

    /**
     * 删除购物车
     * @param ids
     * @throws Exception
     */
    void delShopCar(Integer[] ids) throws Exception;

    /**
     *查询购物车根据ids
     * @param ids
     * @return
     */
    List<ShopCarDTO> findShopByIds(Integer[] ids) throws Exception;

    /**
     * 修改状态
     * @param shopCarDTO
     * @throws Exception
     */
    void updateStatus(ShopCarDTO shopCarDTO) throws Exception;

    /**
     * 提交订单后删除购物车
     * @param ids
     * @throws Exception
     */
    void delShopCarByIds(List<Integer> ids) throws Exception;
}
