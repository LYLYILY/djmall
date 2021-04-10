package com.dj.mall.admin.vo.dict;

import lombok.Data;

@Data
public class DictDataVOResp {

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
