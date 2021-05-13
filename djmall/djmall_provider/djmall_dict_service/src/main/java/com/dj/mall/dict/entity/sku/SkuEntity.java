package com.dj.mall.dict.entity.sku;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_dict_product_sku_gm")
public class SkuEntity {
    /**
     * skuId
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *  商品名称
     */
    private String productType;

    /**
     *  属性id
     */
    private Integer attrId;

}
