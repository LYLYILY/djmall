package com.dj.mall.admin.web.dict.attr;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.constant.AttrConstant;
import com.dj.mall.dict.api.AttrService;
import com.dj.mall.dict.dto.AttrDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attr/")
public class AttrPageController {

    @Reference
    private AttrService attrService;
    @GetMapping("toShow")
    @RequiresPermissions(AttrConstant.PRODUCT_ATTR_MAINTAIN)
    public String toShow() throws Exception {
        return "/dict/attr/show";
    }

    @GetMapping("toRelevance/{id}")
    public String toRelevance(@PathVariable Integer id, ModelMap map) throws Exception {
        AttrDTO attrDTOS = attrService.findAttrById(id);
        map.put("attrName", attrDTOS.getAttrName());
        map.put("id", id);
        return "/dict/attr/relevance";
    }


}
