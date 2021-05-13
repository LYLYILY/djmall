package com.dj.mall.dict.api.data;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.dto.data.DictDataDTO;

import java.util.List;

public interface DictDataService {

    /**
     * 根据parentcode查找字典名
     * @param system
     * @return
     * @throws Exception
     */
    List<DictDataDTO> findDictNameByParentCode(String system) throws Exception;

    /**
     * 字典查询
     * @return
     * @throws Exception
     */
    List<DictDataDTO> findDictDataAll() throws Exception;

    /**
     * 根据code查找字典名
     * @param code
     * @return
     * @throws Exception
     */
    DictDataDTO findDictNameByCode(String code) throws Exception;

    /**
     * 字典修改
     * @param dictDataDTO
     * @throws Exception
     * @throws BusinessException
     */
    void update(DictDataDTO dictDataDTO) throws Exception, BusinessException;

    /**
     * 字典新增
     * @param dictDataDTO
     * @throws Exception
     */
    void add(DictDataDTO dictDataDTO) throws Exception;

    /**
     * 查询字典数据Sex
     * @param sex
     * @return
     * @throws Exception
     */
    List<DictDataDTO> findDictDataSex(String sex) throws Exception;
}