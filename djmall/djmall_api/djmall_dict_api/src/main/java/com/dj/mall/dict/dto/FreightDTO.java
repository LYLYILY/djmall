package com.dj.mall.dict.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FreightDTO implements Serializable {

    /**
     * 主键id
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
