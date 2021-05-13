package com.dj.mall.dict.entity.attr;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_dict_product_attr")
public class AttrEntity {

    /**
     * 商品属性ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品属性名称
     */
    private String attrName;
}
