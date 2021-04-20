package com.dj.mall.dict.mapper.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightBO {

    /**
     *  邮寄id
     */
    private Integer id;

    /**
     * 物流公司名称
     */
    private String logisticsCompany;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     *  物流公司CODE
     *//*
    private String freightCode;*/


}
