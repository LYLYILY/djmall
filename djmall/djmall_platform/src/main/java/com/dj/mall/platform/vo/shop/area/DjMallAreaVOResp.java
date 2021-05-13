package com.dj.mall.platform.vo.shop.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DjMallAreaVOResp {

    /**
     * 地区id
     */
    @TableId(type = IdType.AUTO)
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
