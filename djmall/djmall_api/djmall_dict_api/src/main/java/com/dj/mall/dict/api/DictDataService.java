package com.dj.mall.dict.api;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.dto.DictDataDTO;

import java.util.List;

public interface DictDataService {

    List<DictDataDTO> findDictNameByParentCode(String system) throws Exception;

    List<DictDataDTO> findDictDataAll() throws Exception;

    DictDataDTO findDictNameByCode(String code) throws Exception;

    void update(DictDataDTO dictDataDTO) throws Exception, BusinessException;

    void add(DictDataDTO dictDataDTO) throws Exception;

    List<DictDataDTO> findDictDataSex(String sex) throws Exception;
}