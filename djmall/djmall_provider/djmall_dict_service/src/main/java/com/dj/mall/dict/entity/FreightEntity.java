package com.dj.mall.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("djmall_dict_product_freight")
public class FreightEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
