package com.dj.mall.admin.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.ProductVOReq;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.ProductConstant;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.QiNuiUtil;
import com.dj.mall.product.api.product.ProductService;
import com.dj.mall.product.dto.product.ProductDTO;
import com.dj.mall.user.dto.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Reference
    private ProductService productService;

    /**
     *  添加商品和商品sku
     * @param productVOReq
     * @return
     */
    @PostMapping("addProductSku")
    @RequiresPermissions(ProductConstant.PRODUCT_INSERT_BTN)
    public ResultModel addProductSku(ProductVOReq productVOReq, MultipartFile file) throws Exception {
        Assert.hasText(productVOReq.getProductName(), "名称不能为空");
        Assert.hasText(productVOReq.getProductDisc(), "描述信息不可为空");
        Assert.hasText(productVOReq.getProductType(), "商品类型不可为空");
        Assert.notNull(file, "图片不能为空");
        String firstName = UUID.randomUUID().toString().replace("-", "");
        String lastName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        productVOReq.setProductImg("http://qs0q2d1mv.hn-bkt.clouddn.com/" + firstName + lastName);
        UserDTO userDTO = (UserDTO) SecurityUtils.getSubject().getSession().getAttribute(UserConstant.USER);
        productVOReq.setBusinessId(userDTO.getId());
        productService.addProductSku(DozerUtil.map(productVOReq, ProductDTO.class));
        QiNuiUtil.uploadFile(file.getBytes(), firstName + lastName);
        return new ResultModel().success();
    }

    /**
     * 商品展示
     * @param pageNo
     * @param productVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("show/{pageNo}")
    @RequiresPermissions(ProductConstant.PRODUCT_MANAGER)
    public ResultModel show(@PathVariable Integer pageNo, ProductVOReq productVOReq) throws Exception {
        UserDTO user = (UserDTO) SecurityUtils.getSubject().getSession().getAttribute("USER");
        productVOReq.setRoleId(user.getRoleId());
        productVOReq.setBusinessId(user.getId());
        PageResult page = productService.findProductAll(pageNo, DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success(PageResult.pageInfo(page.getCurrent(), page.getPages(), page.getRecords()));
    }

    /**
     * 修改上下架
     * @param productVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateStatus")
    @RequiresPermissions(ProductConstant.PRODUCT_UPDOWN_BTN)
    public ResultModel updateStatus(ProductVOReq productVOReq) throws Exception {
        productService.updateStatus(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改商品保存
     * @param productVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("update")
    public ResultModel update(ProductVOReq productVOReq, MultipartFile file) throws Exception {
        Assert.hasText(productVOReq.getProductName(), "名称不能为空");
        Assert.hasText(productVOReq.getProductDisc(), "描述信息不可为空");
        Assert.hasText(productVOReq.getProductType(), "商品类型不可为空");
        Assert.notNull(file, "图片不可为空");
        String firstName = UUID.randomUUID().toString().replace("-", "");
        if(!file.isEmpty()){
            String lastName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            QiNuiUtil.delFile(productVOReq.getProductImg());
            productVOReq.setProductImg("http://qs0q2d1mv.hn-bkt.clouddn.com/" + firstName + lastName);
            QiNuiUtil.uploadFile(file.getBytes(), firstName + lastName);
        }
        productService.update(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success();
    }
}
