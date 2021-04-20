package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.role.RoleAuthService;
import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.common.constant.RoleConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role/")
public class RolePageController {

    @Reference
    private RoleAuthService roleAuthService;

    /**
     * 角色展示
     */
    @RequiresPermissions(RoleConstant.ROLE_MANAGER)
    @GetMapping("toShow")
    public String toShow() {
        return "/auth/role/show";
    }

    /**
     * 角色新增
     *
     * @return
     */
    @RequiresPermissions(RoleConstant.ROLE_ADD_BTN)
    @GetMapping("toInsert")
    public String toInsert() {
        return "/auth/role/insert";
    }

    /**
     * 角色修改
     *
     * @return
     */
   @RequiresPermissions(RoleConstant.ROLE_UPDATE_BTN)
   @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, ModelMap map) throws Exception {
        RoleDTO roleName = roleAuthService.findRoleNameById(id);
        map.put("roleName", roleName);
        return "/auth/role/update";
    }

    /**
     * 关联资源
     *
     * @return
     */
    @RequiresPermissions(RoleConstant.ROLE_RESOURCE_BTN)
    @GetMapping("toRoleResource/{roleId}")
    public String toRoleResource(@PathVariable Integer roleId, ModelMap map) {
        map.put("roleId", roleId);
        return "/auth/role/role_resource";
    }

}
