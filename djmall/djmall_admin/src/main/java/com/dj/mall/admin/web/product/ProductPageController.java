package com.dj.mall.admin.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.ProductVOResp;
import com.dj.mall.common.constant.ProductConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.data.DictDataService;
import com.dj.mall.dict.api.freight.FreightService;
import com.dj.mall.dict.dto.data.DictDataDTO;
import com.dj.mall.dict.dto.freight.FreightDTO;
import com.dj.mall.product.api.product.ProductService;
import com.dj.mall.product.dto.product.ProductDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product/")
public class ProductPageController {

    @Reference
    private DictDataService dictDataService;

    @Reference
    private FreightService freightService;

    @Reference
    private ProductService productService;

    /**
     * 商品展示
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping("toShow")
    @RequiresPermissions(ProductConstant.PRODUCT_MANAGER)
    public String toShow(ModelMap map) throws Exception {
        List<DictDataDTO> productList = dictDataService.findDictNameByParentCode("PRODUCT_TYPE");
        map.put("productList", productList);
        return "/product/show";
    }

    /**
     * 商品新增
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping("toAdd")
    @RequiresPermissions(ProductConstant.PRODUCT_INSERT_BTN)
    public String toAdd(ModelMap map) throws Exception {
        List<FreightDTO> freightAll = freightService.freightShow();
        map.put("freightAll",freightAll);
        List<DictDataDTO> productList = dictDataService.findDictNameByParentCode("PRODUCT_TYPE");
        map.put("productList", productList);
        return "/product/add";
    }

    /**
     *  去修改商品
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, ModelMap map) throws Exception {
        List<FreightDTO> freightAll = freightService.freightShow();
        map.put("freightAll",freightAll);
        List<DictDataDTO> productList = dictDataService.findDictNameByParentCode("PRODUCT_TYPE");
        map.put("productList", productList);
        ProductDTO productDTO = productService.findProductById(id);
        map.put("product", DozerUtil.map(productDTO, ProductVOResp.class));
        return "/product/update";
    }
}
