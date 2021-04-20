package com.dj.mall.dict.mapper.bo;

import lombok.Data;

import java.util.List;

@Data
public class AttrValueBO {

    /**
     * 属性值id
     */
    private Integer id;

    /**
     * 属性id
     */
    private Integer attrId;

    /**
     * 属性值名称
     */
    private String attrValue;

    /**
     * 商品属性名称
     */
    private String attrName;

    /**
     *  属性ids
     */
    private String attrIds;

    /**
     * 商品属性值
     */
    private List<String> attrValues;

    /**
     * 商品属性ids
     */
    private List<Integer> attrValueIds;


}
