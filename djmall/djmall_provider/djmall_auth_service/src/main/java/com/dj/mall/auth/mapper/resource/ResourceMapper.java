package com.dj.mall.auth.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.entity.ResourceEntity;
import com.dj.mall.auth.mapper.bo.ResourceBO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ResourceMapper extends BaseMapper<ResourceEntity> {

    /**
     *  查询资源
     */
    List<ResourceBO> findResource() throws DataAccessException;



}
