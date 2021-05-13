package com.dj.mall.dict.dto.sku;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuDTO implements Serializable {

    /**
     *  skuId
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
     *  属性值id
     */
    private List<Integer> ids;

    /**
     *  商品名
     */
    private String productName;
}
