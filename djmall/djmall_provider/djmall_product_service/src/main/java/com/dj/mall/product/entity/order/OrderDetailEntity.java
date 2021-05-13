package com.dj.mall.product.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("djmall_order_detail")
@Data
public class OrderDetailEntity {

    /**
     * 明细ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 子订单号
     */

    private String childOrderNo;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商户ID
     */
    private Integer businessId;

    /**
     * SKUID-针对再次购买时使用
     */
    private Integer skuId;

    /**
     * SKU信息(iphone-红色-64G)
     */
    private String skuInfo;

    /**
     * SKU价格
     */
    private BigDecimal skuPrice;

    /**
     * SKU折扣
     */
    private Integer skuRate;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 支付金额（含运费）
     */
    private BigDecimal payMoney;

    /**
     * 运费
     */
    private BigDecimal freightPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
