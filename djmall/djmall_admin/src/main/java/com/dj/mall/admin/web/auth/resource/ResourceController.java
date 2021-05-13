package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOReq;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.ResourceConstant;
import com.dj.mall.common.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/res/")
public class ResourceController {

    @Reference
    private ResourceService resourceService;

    /**
     * 展示
     * @return
     */
    @RequiresPermissions(ResourceConstant.RESOURCE_MANAGER)
    @RequestMapping("show")
    public ResultModel show() throws Exception {
        List<ResourceDTO> resourceDTOList = resourceService.findResource();
        return new ResultModel().success(DozerUtil.mapList(resourceDTOList, ResourceVOResp.class));
    }

    /**
     * 新增资源
     * @param resourceVOReq
     * @return
     */
    @RequiresPermissions(ResourceConstant.RESOURCE_ADD_BTN)
    @RequestMapping("add")
    public ResultModel add(ResourceVOReq resourceVOReq) throws Exception {
        Assert.hasText(resourceVOReq.getResourceName(), "资源名称不能为空");
        Assert.hasText(resourceVOReq.getUrl(), "资源路径不能为空");
        Assert.hasText(resourceVOReq.getResourceCode(), "资源编码不能为空");
        Assert.hasText(resourceVOReq.getResourceType(), "资源类型不能为空");
        resourceService.addResource(DozerUtil.map(resourceVOReq, ResourceDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改资源
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @RequiresPermissions(ResourceConstant.RESOURCE_UPDATE_BTN)
    @PostMapping("update")
    public ResultModel update(ResourceVOReq resourceVOReq) throws Exception {
        Assert.hasText(resourceVOReq.getResourceName(), "资源名称不能为空");
        Assert.hasText(resourceVOReq.getUrl(), "资源路径不能为空");
        Assert.hasText(resourceVOReq.getResourceCode(), "资源编码不能为空");
        Assert.hasText(resourceVOReq.getResourceType(), "资源类型不能为空");
        resourceService.updateResource(DozerUtil.map(resourceVOReq, ResourceDTO.class));
        return new ResultModel().success();
    }
}
