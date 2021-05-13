package com.dj.mall.platform.vo.shop.shop;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopCarVOResp {

    /**
     *  购物车id
     */
    private Integer id;

    /**
     *  商品名称
     */
    private String productName;

    /**
     *  商品sku名称
     */
    private String skuName;

    /**
     *  商品sku价格
     */
    private BigDecimal skuPrice;

    /**
     *  商品sku邮费
     */
    private BigDecimal freight;

    /**
     *  购买数量
     */
    private Integer buyNum;

    /**
     *  商品折扣
     */
    private Integer skuRate;

    /**
     *  用户id
     */
    private Integer userId;

    /**
     *  商品SkuId
     */
    private Integer skuId;

    /**
     *  状态
     */
    private Integer buyStatus;
}
