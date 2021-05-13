package com.dj.mall.admin.web.dict.sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.attr.AttrValueVOResp;
import com.dj.mall.admin.vo.dict.sku.SkuVOResp;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.SkuConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.sku.SkuService;
import com.dj.mall.dict.dto.attr.AttrValueDTO;
import com.dj.mall.dict.dto.sku.SkuDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku/")
public class SkuController {

    @Reference
    private SkuService skuService;

    /**
     * 通用sku展示
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    @RequiresPermissions(SkuConstant.COMMON_SKU_MAINTAIN)
    public ResultModel show() throws Exception {
        List<SkuDTO> list = skuService.findSkuAll();
        List<SkuVOResp> skuVOResps = DozerUtil.mapList(list, SkuVOResp.class);
        return new ResultModel().success(skuVOResps);
    }

    /**
     * sku关联资源
     */
    @PostMapping("showRelevance")
    @RequiresPermissions(SkuConstant.COMMON_SKU_REL_ATTR_BTN)
    public ResultModel<Object> showRelevance() throws Exception {
        List<SkuDTO> list = skuService.showRelevance();
        List<SkuVOResp> skuVOResps = DozerUtil.mapList(list, SkuVOResp.class);
        return new ResultModel<>().success(skuVOResps);
    }

    /**
     * 关联属性保存
     * @param ids
     * @param productType
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    @RequiresPermissions(SkuConstant.SAVE_REL_ATTR_BTN)
    public ResultModel add(@RequestParam("ids[]") Integer[] ids, String productType) throws Exception {
        skuService.add(ids, productType);
        return new ResultModel().success();
    }

    /**
     * 根据商品类型查找属性值
     * @param productType
     * @return
     * @throws Exception
     */
    @RequestMapping("findAttrValueByProductType")
    public ResultModel findAttrValueByProductType(String productType) throws Exception {
        List<AttrValueDTO> dictAttrValueDTOList = skuService.findAttrValueByProductType(productType);
        return new ResultModel().success(DozerUtil.mapList(dictAttrValueDTOList, AttrValueVOResp.class));
    }
}
