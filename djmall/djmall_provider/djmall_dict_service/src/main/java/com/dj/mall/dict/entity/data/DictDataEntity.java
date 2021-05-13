package com.dj.mall.dict.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_dict")
public class DictDataEntity {

    /**
     *  code
     */
    private String code;

    /**
     *  上级code
     */
    private String parentCode;

    /**
     *  字典名
     */
    private String dictName;


}
