package com.dj.mall.admin.web.product.sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.ProductSkuVOReq;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.product.ProductSkuService;
import com.dj.mall.product.dto.product.ProductSkuDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productSku/")
public class ProductSkuController {

    @Reference
    private ProductSkuService productSkuService;

    @PostMapping("findSkuByProductId/{id}")
    public ResultModel findSkuByProductId(@PathVariable Integer id) throws Exception {
        List<ProductSkuDTO> list = productSkuService.findSkuByProductId(id);
        return new ResultModel().success(list);
    }

    /**
     * 修改库存保存
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateSkuCount")
    public ResultModel updateSkuCount(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuService.updateSkuCount(DozerUtil.map(productSkuVOReq, ProductSkuDTO.class));
        return new ResultModel().success();
    }

    /**
     * 编辑保存
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateSku")
    public ResultModel updateSku(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuService.updateSku(DozerUtil.map(productSkuVOReq, ProductSkuDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改默认
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateDefault")
    public ResultModel updateDefault(ProductSkuVOReq productSkuVOReq) throws Exception, BusinessException {
        productSkuService.updateDefault(DozerUtil.map(productSkuVOReq, ProductSkuDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改下架
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("downShelf")
    public ResultModel downShelf(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuService.downShelf(DozerUtil.map(productSkuVOReq, ProductSkuDTO.class));
        return new ResultModel().success();
    }
}
