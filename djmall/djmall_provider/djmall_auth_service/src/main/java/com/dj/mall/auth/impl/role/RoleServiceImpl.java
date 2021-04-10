package com.dj.mall.auth.impl.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.api.role.RoleAuthService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.auth.dto.TreeDataDTO;
import com.dj.mall.auth.entity.RoleEntity;
import com.dj.mall.auth.entity.RoleResourceEntity;
import com.dj.mall.auth.mapper.role.RoleMapper;
import com.dj.mall.auth.service.role.RoleResourceService;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleAuthService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;


    /**
     * 角色展示
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<RoleDTO> findRoleAll() throws Exception {
        List<RoleEntity> list = super.list();
        return DozerUtil.mapList(list, RoleDTO.class);
    }

    /**
     * 角色新增
     */
    @Override
    public void addRole(RoleDTO roleDTO) throws Exception {
        super.save(DozerUtil.map(roleDTO, RoleEntity.class));
    }

    /**
     * 新增查重
     */
    @Override
    public boolean getRoleName(String roleName) throws Exception {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", roleName);
        RoleEntity roleEntity = super.getOne(wrapper);
        if (null != roleEntity) {
            return false;
        }
        return true;
    }

    /**
     * 根据id查roleName
     */
    @Override
    public RoleDTO findRoleNameById(Integer id) throws Exception {
        RoleEntity role = roleMapper.selectById(id);
        return DozerUtil.map(role, RoleDTO.class);
    }

    /**
     * 角色修改
     *
     * @param roleDTO
     */
    @Override
    public void updateRole(RoleDTO roleDTO) throws Exception {
        super.saveOrUpdate(DozerUtil.map(roleDTO, RoleEntity.class));
    }

    /**
     * 角色修改查重
     *
     * @param roleName
     * @param id
     */
    @Override
    public boolean findRoleName(String roleName, Integer id) throws Exception {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", roleName);
        RoleEntity roleEntity = super.getOne(wrapper);
        if (null != roleEntity && !roleEntity.getId().equals(id)){
            throw new BusinessException("角色名已存在");
        }
        return true;
    }

    @Override
    public List<TreeDataDTO> getRoleResourceTree(Integer roleId) throws Exception {
        //获取全部资源
        List<ResourceDTO> resourceAll = resourceService.findResource();
        //获取角色资源表数据
        QueryWrapper<RoleResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        List<RoleResourceEntity> list = roleResourceService.list(queryWrapper);
        List<TreeDataDTO> treeDataDTOList = new ArrayList<>();
        //遍历resourceAll，list并比较角色id
        resourceAll.forEach(resources -> {
            TreeDataDTO treeDataDTO = new TreeDataDTO();
            treeDataDTO.setId(resources.getId());
            treeDataDTO.setParentId(resources.getParentId());
            treeDataDTO.setName(resources.getResourceName());
            /*for (RoleResourceEntity roleResourceEntity : list) {
                if (roleResourceEntity.getResourceId().equals(resources.getId())) {
                    treeDataDTO.setChecked(true);
                    break;
                }
            }*/

            treeDataDTO.setChecked(list.stream().filter(roleResourceEntity -> roleResourceEntity.getResourceId().equals(resources.getId())).findAny().isPresent());
            treeDataDTOList.add(treeDataDTO);
        });
        return treeDataDTOList;
    }

    @Override
    public void addRoleResource(RoleDTO roleDTO) throws Exception {
        /*//删除role，resource关联表的历史数据
        QueryWrapper<RoleResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleDTO.getId());
        roleResourceService.remove(queryWrapper);
        //遍历resourceIds并重新组装数据save保存
        List<RoleResourceEntity> list = new ArrayList<>();
        roleDTO.getIds().forEach(resourceId -> {
            RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
            roleResourceEntity.setRoleId(roleDTO.getId());
            roleResourceEntity.setResourceId(resourceId);
            list.add(roleResourceEntity);
        });
        roleResourceService.saveBatch(list);*/

        //获取历史数据
        QueryWrapper<RoleResourceEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleDTO.getId());
        List<RoleResourceEntity> historyList = roleResourceService.list(queryWrapper);
        //把historyList中的全部资源ID组成一个集合
        List<Integer> historyIds = historyList.stream().map(RoleResourceEntity::getResourceId).collect(Collectors.toList());
        //新的对历史取差集  -  新增
        List<Integer> addResourceIds = roleDTO.getIds().stream().filter(newId -> !historyIds.contains(newId)).collect(Collectors.toList());
        //历史对新的取差集  -  删除
        List<Integer> removeResourceIds = historyIds.stream().filter(historyId -> !roleDTO.getIds().contains(historyId)).collect(Collectors.toList());
        //新增
        List<RoleResourceEntity> list = new ArrayList<>();
        addResourceIds.forEach(resourceId -> {
            RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
            roleResourceEntity.setRoleId(roleDTO.getId());
            roleResourceEntity.setResourceId(resourceId);
            list.add(roleResourceEntity);
        });
        roleResourceService.saveBatch(list);
        //删除
        QueryWrapper<RoleResourceEntity> queryWrapper1 = new QueryWrapper();
        if (removeResourceIds.size() > 0) {
            queryWrapper1.eq("role_id", roleDTO.getId());
            queryWrapper1.in("resource_id", removeResourceIds);
            roleResourceService.remove(queryWrapper1);
        }


    }


}