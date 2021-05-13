package com.dj.mall.dict.dto.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictDataDTO implements Serializable {

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
