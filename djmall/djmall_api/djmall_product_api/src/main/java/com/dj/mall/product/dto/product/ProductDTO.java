package com.dj.mall.product.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO implements Serializable {

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
     *  邮费加公司
     */
    private String feeExp;

    /**
     *  sku集合
     */
    private List<ProductSkuDTO> skuList;

    /**
     *  商户id
     */
    private Integer businessId;

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
    private List<String> productTypeList;

    /**
     *  商品id
     */
    private Integer productId;

    /**
     * 用户级别/角色
     */
    private Integer roleId;

    /**
     * 最小价格
     */
    private Double skuPriceMin;

    /**
     * 最大价格
     */
    private Double skuPriceMax;

    /**
     * sku价格
     */
    private BigDecimal skuPrice;

    /**
     * sku库存
     */
    private Integer skuCount;

    /**
     * sku折扣
     */
    private Integer skuRate;

    /**
     * sku属性值名集合
     */
    private String skuName;





    /**
     * sku状态
     */
    private String skuStatus;

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
     *  用户id
     */
    private Integer userId;

    /**
     *  点赞状态
     */
    private Integer likeStatus;
}
