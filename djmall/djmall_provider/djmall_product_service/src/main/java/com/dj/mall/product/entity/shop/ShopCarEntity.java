package com.dj.mall.product.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_user_shop_car")
public class ShopCarEntity {

    /**
     *  主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *  商品skuid
     */
    private Integer skuId;

    /**
     *  用户
     */
    private Integer userId;

    /**
     *  购买数量
     */
    private Integer buyNum;

    /**
     *  状态
     */
    private Integer buyStatus;


}
