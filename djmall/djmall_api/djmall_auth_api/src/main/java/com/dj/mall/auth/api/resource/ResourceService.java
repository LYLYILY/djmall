package com.dj.mall.auth.api.resource;


import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface ResourceService {

    /**
     *  展示资源列表
     */
    List<ResourceDTO> findResource() throws Exception;

    /**
     *  根据ID查找资源
     */
    ResourceDTO findResourceNameById(Integer id) throws Exception;

    /**
     *  添加资源
     */
    void addResource(ResourceDTO map) throws BusinessException;

    /**
     *  修改资源
     */
    void updateResource(ResourceDTO map) throws Exception, BusinessException;



}
