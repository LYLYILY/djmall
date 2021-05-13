package com.dj.mall.admin.web.product.sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productSku/")
public class ProductSkuPageController {

    @Reference
    private ProductSkuService productSkuService;

    /**
     * 修改库存
     * @param id
     * @param modelMap
     * @return
     * @throws Exception
     */
    @GetMapping("toUpdateSkuCount")
    private String updateSkuCount(Integer id, Integer productId, ModelMap modelMap) throws Exception{
        ProductSkuDTO skuDTO = productSkuService.findSkuById(id);
        modelMap.put("count", skuDTO.getSkuCount());
        modelMap.put("id", skuDTO.getId());
        modelMap.put("productId", productId);
        return "/product/sku_count";
    }

    /**
     * 去编辑
     * @param id
     * @param productId
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping("toUpdateSku")
    public String toUpdateSku(Integer id, Integer productId, ModelMap map) throws Exception {
        ProductSkuDTO skuDTO = productSkuService.findSkuById(id);
        map.put("skuDTO", skuDTO);
        map.put("productId", productId);
        return "/product/sku_update";
    }

}
