package com.dj.mall.platform.vo.product;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductVOReq {
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
     *  sku集合
     */
    private List<ProductSkuVOReq> skuList;

    /**
     *  商户id
     */
    private Integer businessId;

    /**
     * 快递公司id
     */
    private Integer freightId;

    /**
     * 商品类型集合
     */
    private List<String> productTypeList;

    /**
     * 用户级别/角色
     */
    private Integer roleId;

    /**
     *  用户id
     */
    private Integer userId;

    /**
     * sku价格
     */
    private BigDecimal skuPrice;

    /**
     * 最小价格
     */
    private Double skuPriceMin;

    /**
     * 最大价格
     */
    private Double skuPriceMax;

}
