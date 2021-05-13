package com.dj.mall.dict.dto.attr;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrValueDTO implements Serializable {

    /**
     * 商品属性id
     */
    private Integer id;

    /**
     * 商品属性值
     */
    private String  attrValue;

    /**
     * 商品属性id
     */
    private String  attrId;

    /**
     * 商品属性名称
     */
    private String attrName;

    /**
     * 商品属性值
     */
    private List<String> attrValues;

    /**
     * 商品属性ids
     */
    private List<Integer> attrValueIds;

}
