package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.auth.api.role.RoleAuthService;
import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.common.constant.DictConstant;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.dict.api.DictDataService;
import com.dj.mall.dict.dto.DictDataDTO;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/")
public class UserPageController {

    @Reference
    private UserService userService;

    @Reference
    private RoleAuthService roleAuthService;

    @Reference
    private DictDataService dictDataService;

    /**
     * 去登录
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "auth/user/login";
    }

    /**
     * 去注册
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("toAdd")
    public String toAdd(ModelMap map) throws Exception{
        List<RoleDTO> list = roleAuthService.findRoleAll();
        List<RoleVOResp> roleVOResps = DozerUtil.mapList(list, RoleVOResp.class);
        List<DictDataDTO> sexList = dictDataService.findDictDataSex(DictConstant.SEX);
        map.put("sexList", sexList);
        map.put("roleVOResps", roleVOResps);
        map.put("salt", PasswordSecurityUtil.generateSalt());
        return "auth/user/add";
    }

    /**
     * 去展示
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions(UserConstant.USER_MANAGER)
    @GetMapping("toShow")
    public String toShow(ModelMap map) throws Exception {
        List<RoleDTO> role = roleAuthService.findRoleAll();
        List<DictDataDTO> sexList = dictDataService.findDictDataSex(DictConstant.SEX);
        map.put("sexList", sexList);
        map.put("role", role);
        return "/auth/user/show";
    }

    /**
     * 去授权
     * @param id
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions(UserConstant.USER_PERMISSION_BTN)
    @GetMapping("toShowUth/{id}")
    public String toShowUth(@PathVariable Integer id, ModelMap map) throws Exception {
        Integer roleId = userService.findRoleByUserId(id);
        map.put("roleId", roleId);
        map.put("userId", id);
        return "/auth/user/uth";
    }

    /**
     * 去修改
     * @param userId
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions(UserConstant.USER_UPDATE_BTN)
    @GetMapping("toUpd/{userId}")
    public String toUpd(@PathVariable Integer userId, ModelMap map) throws Exception {
        UserDTO userDTO = userService.findUserByUserId(userId);
        UserVOResp user = DozerUtil.map(userDTO, UserVOResp.class);
        List<DictDataDTO> sexList = dictDataService.findDictDataSex(DictConstant.SEX);
        map.put("sexList", sexList);
        map.put("user", user);
        return "/auth/user/update";
    }
}
