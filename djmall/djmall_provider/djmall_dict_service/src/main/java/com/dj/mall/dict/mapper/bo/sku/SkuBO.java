package com.dj.mall.dict.mapper.bo.sku;

import lombok.Data;

@Data
public class SkuBO {

    /**
     *  skuid
     */
    private Integer id;
    /**
     *  商品名称
     */
    private String productType;

    /**
     *  属性id
     */
    private Integer attrId;

    /**
     *  属性名
     */
    private String attrName;

    /**
     *  属性值
     */
    private String attrValue;

    /**
     *  商品名
     */
    private String productName;
}
