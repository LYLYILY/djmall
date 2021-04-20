package com.dj.mall.admin.vo.freight;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightVOResp {

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
}
