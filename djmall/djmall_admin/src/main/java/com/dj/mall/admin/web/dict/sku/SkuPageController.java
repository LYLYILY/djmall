package com.dj.mall.admin.web.dict.sku;

import com.dj.mall.common.constant.SkuConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sku/")
public class SkuPageController {

    @GetMapping("toShow")
    @RequiresPermissions(SkuConstant.COMMON_SKU_MAINTAIN)
    public String toShow() {
        return "/dict/sku/show";
    }

    @GetMapping("toAdd/{productType}")
    @RequiresPermissions(SkuConstant.COMMON_SKU_REL_ATTR_BTN)
    public String toAdd(@PathVariable String productType,  ModelMap map) {
        map.put("productType", productType);
        return "/dict/sku/add";
    }
}
