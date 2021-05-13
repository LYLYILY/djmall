package com.dj.mall.product.dto.area;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DjMallAreaDTO {

    /**
     * 地区id
     */
    private Integer id;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区名称拼音
     */
    private String areaPinyin;

    /**
     * 地区父级id
     */
    private Integer areaParentId;


}
