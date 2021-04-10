package com.dj.mall.admin.web.index;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/index/")
public class IndexController {

    @Reference
    private UserService userService;

    @GetMapping("getLeft")
    private ResultModel<Object> getLeft(HttpSession session) throws Exception {
        //当前登录用户的资源
        UserDTO user = (UserDTO) session.getAttribute("USER");
        List<MenuDTO> menuDTOS = user.getResourceList(); //userService.getLeft(user.getId());
        return new ResultModel<>().success(DozerUtil.mapList(menuDTOS, ResourceVOResp.class));
    }



}
