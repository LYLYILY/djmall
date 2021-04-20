package com.dj.mall.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.entity.AttrEntity;
import com.dj.mall.dict.mapper.bo.AttrBO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface AttrMapper extends BaseMapper<AttrEntity> {

    /**
     * 商品属性维护展示
     * @return
     */
    List<AttrBO> findAttrAll() throws DataAccessException;
}
