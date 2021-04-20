package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.common.constant.ResourceConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/res/")
public class ResourcePageController {

    @Reference
    private ResourceService resourceService;

    /**
     * 去展示资源
     * @return
     */
    @RequiresPermissions(ResourceConstant.RESOURCE_MANAGER)
    @RequestMapping("toShow")
    public String toShow() {
        return "/auth/resource/show";
    }

    /**
     * 去新增资源
     * @param id  上级id
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions(ResourceConstant.RESOURCE_ADD_BTN)
    @RequestMapping("toAdd")
    public String toAdd(Integer id, ModelMap map) throws Exception {
        ResourceDTO resourceDTO = resourceService.findResourceNameById(id);
        map.put("id", id);
        map.put("resourceName", "顶级");
        if (!id.equals(ResourceConstant.ZERO)) {
            map.put("resourceName", resourceDTO.getResourceName());
        }
        return "/auth/resource/add";
    }

    /**
     * 403
     * @return
     */
    @GetMapping("toError")
    public String toError() {
        return "/error";
    }

}
