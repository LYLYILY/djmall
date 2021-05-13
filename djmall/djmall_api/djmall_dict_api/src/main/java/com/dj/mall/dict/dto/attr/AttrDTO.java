package com.dj.mall.dict.dto.attr;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrDTO implements Serializable {

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
