package com.dj.mall.admin.vo.dict.attr;

import lombok.Data;

import java.util.List;

@Data
public class AttrValueVOResp {
    private Integer id;

    /**
     * 商品属性值
     */
    private String  attrValue;

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
