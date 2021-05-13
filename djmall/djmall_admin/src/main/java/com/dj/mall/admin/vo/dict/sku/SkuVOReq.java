package com.dj.mall.admin.vo.dict.sku;

import lombok.Data;

@Data
public class SkuVOReq {

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
     *  商品名
     */
    private String productName;

}
