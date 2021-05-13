package com.dj.mall.auth.api.role;



import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.role.RoleDTO;
import com.dj.mall.auth.dto.TreeDataDTO;

import java.util.List;

public interface RoleAuthService {

    /**
     * 角色展示
     * @return
     * @throws Exception
     */
    List<RoleDTO> findRoleAll() throws Exception;

    /**
     * 角色新增
     * @param roleDTO
     * @throws Exception
     */
    void addRole(RoleDTO roleDTO) throws Exception;

    /**
     * 角色新增查重
     * @param roleName
     * @return
     * @throws Exception
     */
    boolean getRoleName(String roleName) throws Exception;

    /**
     * 根据id查roleName
     * @param id
     * @return
     * @throws Exception
     */
    RoleDTO findRoleNameById(Integer id) throws Exception;

    /**
     * 角色修改
     * @param roleDTO
     * @throws Exception
     */
    void updateRole(RoleDTO roleDTO) throws Exception;

    /**
     * 角色修改查重
     * @param roleName
     * @param id
     * @return
     * @throws Exception
     */
    boolean findRoleName(String roleName, Integer id) throws Exception;

    /**
     * 关联资源
     * @param roleId
     * @return
     * @throws Exception
     */
    List<TreeDataDTO> getRoleResourceTree(Integer roleId) throws Exception;

    /**
     * 关联资源保存
     * @param roleDTO
     * @throws Exception
     */
    void addRoleResource(RoleDTO roleDTO) throws Exception;

    /**
     * 根据角色ID查询资源信息
     *
     * @param roleId
     */
    List<ResourceDTO> getRoleResource(Integer id) throws Exception;
}
