package com.dj.mall.auth.api.role;



import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.auth.dto.TreeDataDTO;

import java.util.List;

public interface RoleAuthService {

    /**
     * 展示角色列表
     */
    List<RoleDTO> findRoleAll() throws Exception;

    /**
     * 角色新增
     */
    void addRole(RoleDTO roleDTO) throws Exception;

    /**
     * 角色新增查重
     */
    boolean getRoleName(String roleName) throws Exception;

    /**
     * 根据id查roleName
     */
    RoleDTO findRoleNameById(Integer id) throws Exception;

    /**
     * 角色修改
     */
    void updateRole(RoleDTO roleDTO) throws Exception;

    /**
     * 角色修改查重
     */
    boolean findRoleName(String roleName, Integer id) throws Exception;

    /**
     * 关联资源
     */
    List<TreeDataDTO> getRoleResourceTree(Integer roleId) throws Exception;

    /**
     * 关联资源保存
     * @param roleDTO
     * @throws Exception
     */
    void addRoleResource(RoleDTO roleDTO) throws Exception;


}
