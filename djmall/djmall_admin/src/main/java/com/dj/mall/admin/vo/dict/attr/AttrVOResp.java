package com.dj.mall.admin.vo.dict.attr;

import lombok.Data;

@Data
public class AttrVOResp {

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
