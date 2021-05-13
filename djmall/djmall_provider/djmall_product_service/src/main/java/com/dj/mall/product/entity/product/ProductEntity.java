package com.dj.mall.product.entity.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_product")
public class ProductEntity {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
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
     * 快递公司id
     */
    private Integer freightId;
}
