package com.dj.mall.platform.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.dict.api.data.DictDataService;
import com.dj.mall.dict.dto.data.DictDataDTO;
import com.dj.mall.product.api.product.ProductService;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import com.dj.mall.user.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product/")
public class ProductPageController {

    @Reference
    private DictDataService dictDataService;

    @Reference
    private ProductSkuService productSkuService;

    @Reference
    private ProductService productService;


    @Reference
    private UserService userService;

    /**
     * 展示商品
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    public String toShow(ModelMap map) throws Exception {
        List<DictDataDTO> productList = dictDataService.findDictNameByParentCode("PRODUCT_TYPE");
        map.put("productList", productList);
        return "/product/show";
    }

    /**
     * 订单详情-选择商品信息
     * @param productId
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping("toShowDetail")
    public String toShowDetail(Integer productId, ModelMap map) throws Exception {
        List<ProductSkuDTO> product = productSkuService.findSkuNameById(productId);
        map.put("product", product);
        return "/product/show_detail";
    }


}
