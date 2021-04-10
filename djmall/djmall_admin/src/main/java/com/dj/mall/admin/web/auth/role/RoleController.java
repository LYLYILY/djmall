package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.auth.api.role.RoleAuthService;
import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.auth.dto.TreeDataDTO;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role/")
public class RoleController {

    /**
     * 角色service
     */
    @Reference
    private RoleAuthService roleAuthService;

    /**
     * 角色展示
     */
    @PostMapping("show")
    public ResultModel<Object> show() throws Exception {
        List<RoleDTO> list = roleAuthService.findRoleAll();
        List<RoleVOResp> roleVOResps = DozerUtil.mapList(list, RoleVOResp.class);
        return new ResultModel<>().success(roleVOResps);
    }

    /**
     * 角色新增
     */
    @PostMapping("insert")
    public ResultModel insert(RoleVOReq roleVOReq) throws Exception {
        roleAuthService.addRole(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel().success();
    }

    /**
     * 新增查重
     * @param roleName
     * @return
     */
    @PostMapping("getRoleName")
    public boolean getRoleName(String roleName) throws Exception {
        return roleAuthService.getRoleName(roleName);
    }

    /**
     * 角色修改
     */
    @PostMapping("update")
    public ResultModel updateRole(RoleVOReq roleVOReq) throws Exception {
        roleAuthService.updateRole(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改查重
     */
    @PostMapping("findRoleName/{id}")
    public boolean findRoleName(@PathVariable Integer id, String roleName) throws Exception {
        return roleAuthService.findRoleName(roleName, id);
    }

    /**
     * 关联资源
     */
    @GetMapping("getRoleResourceTree/{roleId}")
    public ResultModel getRoleResourceTree(@PathVariable Integer roleId) throws Exception {
        List<TreeDataDTO> treeDataList = roleAuthService.getRoleResourceTree(roleId);
        return new ResultModel().success(treeDataList);
    }

    @PostMapping("addRoleResource")
    public ResultModel addRoleResource(RoleVOReq roleVOReq) throws Exception {
        roleAuthService.addRoleResource(DozerUtil.map(roleVOReq, RoleDTO.class));
        return new ResultModel().success();
    }


}
