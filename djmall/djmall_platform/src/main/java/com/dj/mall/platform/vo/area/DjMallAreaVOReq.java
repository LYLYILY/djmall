package com.dj.mall.platform.vo.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DjMallAreaVOReq {
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
