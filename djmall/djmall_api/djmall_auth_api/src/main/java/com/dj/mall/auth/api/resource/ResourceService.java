package com.dj.mall.auth.api.resource;


import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface ResourceService {

    /**
     * 展示资源
     * @return
     * @throws Exception
     */
    List<ResourceDTO> findResource() throws Exception;

    /**
     * 根据id查资源名称
     * @param id 资源id
     * @return
     * @throws Exception
     */
    ResourceDTO findResourceNameById(Integer id) throws Exception;

    /**
     * 新增资源
     * @param map
     * @throws Exception
     */
    void addResource(ResourceDTO map) throws BusinessException;

    /**
     * 修改资源
     * @param map
     * @throws Exception
     */
    void updateResource(ResourceDTO map) throws Exception, BusinessException;


    List<ResourceDTO> findResourceById(List<Integer> addRedis) throws Exception;
}
