package com.dj.mall.dict.api;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.dto.AttrDTO;
import com.dj.mall.dict.dto.AttrValueDTO;

import java.util.List;

public interface AttrService {

    /**
     * 商品属性维护展示
     * @return
     * @throws Exception
     */
    List<AttrDTO> findAttrAll() throws Exception;

    /**
     * 商品属性维护新增
     * @param attrDTO
     * @throws Exception
     * @throws BusinessException
     */
    void add(AttrDTO attrDTO) throws Exception, BusinessException;

    /**
     * 查询商品属性名是否重复
     *
     * @param attrName
     * @return
     * @throws Exception
     */
    boolean findAttrName(String attrName) throws Exception;

    /**
     * 根据id查询attrName
     * @param id
     * @return
     */
    AttrDTO findAttrById(Integer id) throws Exception;

    /**
     * 查询attr关联属性
     * @param id
     * @return
     * @throws Exception
     */
    List<AttrValueDTO> findAttrValueById(Integer id) throws Exception;

    /**
     * 新增商品属性值查重
     * @param attrValue
     * @return
     */
    boolean findAttrValueName(String attrValue) throws Exception;

    /**
     * 商品属性新增
     * @param attrValueDTO
     * @throws Exception
     */
    void insert(AttrValueDTO attrValueDTO) throws Exception;

    /**
     * 删除attr关联属性
     * @param id
     * @throws Exception
     */
    void delAttrValueById(Integer id) throws Exception;
}
