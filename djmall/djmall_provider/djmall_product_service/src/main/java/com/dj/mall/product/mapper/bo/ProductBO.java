package com.dj.mall.product.mapper.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductBO implements Serializable {
    /**
     * 商品id
     */
    private Integer id;

    /**
     * 商品名字
     */
    private String productName;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 商品状态
     */
    private String productStatus;

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 商品描述
     */
    private String productDisc;

    /**
     * 点赞量
     */
    private Integer likeNum;

    /**
     * 订单量
     */
    private Integer orderNum;

    /**
     * 商品类型展示
     */
    private String productTypeShow;

    /**
     * 商品状态展示
     */
    private String productStatusShow;

    /**
     * 邮费
     */
    private Double freight;

    /**
     * 快递公司
     */
    private String logisticsCompany;

    /**
     * 快递公司id
     */
    private Integer freightId;

    /**
     * 商品类型集合
     */
    private String[] productTypeList;

    /**
     * 用户级别/角色
     */
    private Integer roleId;

    /**
     *  商户id
     */
    private Integer businessId;

    /**
     * sku库存
     */
    private Integer skuCount;

    /**
     * sku折扣
     */
    private Integer skuRate;

    /**
     * sku价格
     */
    private BigDecimal skuPrice;




}
