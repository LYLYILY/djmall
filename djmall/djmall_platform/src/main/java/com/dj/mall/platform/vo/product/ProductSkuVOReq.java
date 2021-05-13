package com.dj.mall.platform.vo.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductSkuVOReq {
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

    /**
     * 商品名
     */
    private String productNameShow;

    /**
     * 商品类型
     */
    private String productTypeShow;

    /**
     * 邮费
     */
    private String freightShow;

    /**
     * 图片
     */
    private String productImgShow;

    /**
     * 描述
     */
    private String productDescShow;

    /**
     * 点赞量
     */
    private String likeNumShow;

    /**
     * 订单量
     */
    private String orderNumShow;

    /**
     * 最小价格
     */
    private Double skuPriceMin;

    /**
     * 最大价格
     */
    private Double skuPriceMax;

    /**
     * 商品类型集合
     */
    private String[] productTypeList;

    /**
     *  多个值
     */
    private String productKeyWords;

    /**
     *  购买数量
     */
    private Integer buyCount;

    /**
     *  现价
     */
    private BigDecimal totalPayMoney;

    /**
     *  邮费
     */
    private BigDecimal freight;

    /**
     *  点赞总量
     */
    private Integer likeNum;

    /**
     *  用户id
     */
    private Integer userId;
}
