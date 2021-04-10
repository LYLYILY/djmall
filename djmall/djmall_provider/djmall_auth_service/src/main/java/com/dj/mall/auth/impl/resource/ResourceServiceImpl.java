package com.dj.mall.auth.impl.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.auth.entity.ResourceEntity;
import com.dj.mall.auth.mapper.bo.ResourceBO;
import com.dj.mall.auth.mapper.resource.ResourceMapper;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;

import java.util.List;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, ResourceEntity> implements ResourceService {

    @Reference
    private ResourceService resourceService;
    /**
     * 展示资源
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ResourceDTO> findResource() throws Exception {
        List<ResourceBO> resourceBos = super.baseMapper.findResource();
        return DozerUtil.mapList(resourceBos, ResourceDTO.class);
    }

    /**
     * 根据id查资源名称
     *
     * @param id 资源id
     * @return
     * @throws Exception
     */
    @Override
    public ResourceDTO findResourceNameById(Integer id) throws Exception {
        ResourceEntity resourceEntity = super.getById(id);
        return DozerUtil.map(resourceEntity, ResourceDTO.class);
    }

    /**
     * 新增资源
     *
     * @param resourceDTO
     * @throws Exception
     */
    @Override
    public void addResource(ResourceDTO resourceDTO) throws BusinessException {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_name", resourceDTO.getResourceName());
        ResourceEntity resource = super.getOne(queryWrapper);
        if(null != resource){
            throw new BusinessException("资源名重复");
        }
        //资源编码大写处理
        resourceDTO.setResourceCode(resourceDTO.getResourceCode().toUpperCase());
        super.save(DozerUtil.map(resourceDTO, ResourceEntity.class));
    }

    /**
     * 修改资源
     *
     * @param resourceDTO
     * @throws Exception
     */
    @Override
    public void updateResource(ResourceDTO resourceDTO) throws Exception, BusinessException{
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_name", resourceDTO.getResourceName());
        ResourceEntity resource = super.getOne(queryWrapper);
        if(null != resource && resourceDTO.getId() != resource.getId()){
            throw new BusinessException("资源名重复");
        }
        super.updateById(DozerUtil.map(resourceDTO, ResourceEntity.class));
    }



}
