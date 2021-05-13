package com.dj.mall.platform.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.platform.vo.product.ProductSkuVOResp;
import com.dj.mall.platform.vo.product.ProductVOReq;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.dto.product.ProductDTO;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Reference
    private ProductSkuService productSkuService;

    /**
     * 商品sku展示
     * @param pageNo
     * @return
     * @throws Exception
     */
    @RequestMapping("show/{pageNo}")
    public ResultModel show(@PathVariable Integer pageNo, ProductVOReq productVOReq) throws Exception{
        PageResult page = productSkuService.findProductSkuAll(pageNo, DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success(PageResult.pageInfo(page.getCurrent(), page.getPages(), page.getRecords()));
        /*List<ProductDTO> productSkuAll = productSkuService.findProductSkuAll(pageNo, DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success(productSkuAll);*/
    }

    /**
     * 根据sku属性查找数据
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("getProductById")
    public ResultModel getProductById(Integer id) throws Exception {
        ProductSkuDTO productSkuDTO = productSkuService.findProductById(id);
        return new ResultModel().success(DozerUtil.map(productSkuDTO, ProductSkuVOResp.class));
    }
}
