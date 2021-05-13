package com.dj.mall.product.entity.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("djmall_area")
public class DjMallAreaEntity implements Serializable {

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
