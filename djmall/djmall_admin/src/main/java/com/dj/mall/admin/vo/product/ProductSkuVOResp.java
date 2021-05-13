package com.dj.mall.admin.vo.product;

import lombok.Data;

import java.util.Date;

@Data
public class ProductSkuVOResp {
    /**
     * 商品skuid
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * sku价格
     */
    private double skuPrice;

    /**
     * sku库存
     */
    private Integer skuCount;

    /**
     * sku折扣
     */
    private Integer skuRate;

    /**
     * sku状态
     */
    private String skuStatus;

    /**
     * sku属性值名集合
     */
    private String skuName;

    /**
     * 商户Id
     */
    private Integer businessId;

    /**
     * 是否默认
     */
    private String isDefault;

    /**
     * 更新时间
     */
    private Date updateTime;
}
