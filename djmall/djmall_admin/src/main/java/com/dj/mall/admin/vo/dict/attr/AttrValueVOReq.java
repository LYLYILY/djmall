package com.dj.mall.admin.vo.dict.attr;

import lombok.Data;

@Data
public class AttrValueVOReq {
    private Integer id;

    /**
     * 商品属性值
     */
    private String  attrValue;

    /**
     * 商品属性id
     */
    private String  attrId;
}
