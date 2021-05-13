package com.dj.mall.dict.mapper.bo.attr;

import lombok.Data;

@Data
public class AttrBO {

    /**
     * 商品属性ID
     */
    private Integer id;

    /**
     * 商品属性名称
     */
    private String attrName;

    /**
     * 商品属性值
     */
    private String attrValue;
}
