package com.dj.mall.admin.web.index;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.CacheKeyConsant;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/index/")
public class IndexController {

    @Reference
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("getLeft")
    private ResultModel<Object> getLeft(HttpSession session) throws Exception {
        //当前登录用户的资源
        UserDTO user = (UserDTO) session.getAttribute(UserConstant.USER);
        //List<MenuDTO> menuDTOS = user.getResourceList(); //userService.getLeft(user.getId());
        /*List<MenuDTO> menuDTOS = user.getResourceList().stream().filter(menuDTO -> menuDTO.getResourceType().equals(1)).collect(Collectors.toList());*/
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<ResourceDTO> resourceList = hashOperations.values(CacheKeyConsant.ROLE_ + user.getRoleId());
        List<ResourceDTO> resourceDTOS = resourceList.stream().filter(resourceDTO -> resourceDTO.getResourceType().equals(1)).collect(Collectors.toList());
        return new ResultModel<>().success(DozerUtil.mapList(resourceDTOS, ResourceVOResp.class));
    }
}
