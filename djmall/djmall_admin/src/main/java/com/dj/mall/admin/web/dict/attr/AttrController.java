package com.dj.mall.admin.web.dict.attr;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.attr.AttrVOReq;
import com.dj.mall.admin.vo.dict.attr.AttrVOResp;
import com.dj.mall.admin.vo.dict.attr.AttrValueVOReq;
import com.dj.mall.admin.vo.dict.attr.AttrValueVOResp;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.AttrConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.attr.AttrService;
import com.dj.mall.dict.dto.attr.AttrDTO;
import com.dj.mall.dict.dto.attr.AttrValueDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attr/")
public class AttrController {

    @Reference
    private AttrService attrService;

    /**
     * 商品属性维护展示
     * @return
     * @throws Exception
     */
    @RequestMapping("show")
    @RequiresPermissions(AttrConstant.PRODUCT_ATTR_MAINTAIN)
    public ResultModel show() throws Exception {
        List<AttrDTO> list = attrService.findAttrAll();
        return new ResultModel().success(DozerUtil.mapList(list, AttrVOResp.class));
    }

    /**
     * 商品属性维护新增
     * @param attrVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    @RequiresPermissions(AttrConstant.ATTR_INSERT_BTN)
    public ResultModel add(AttrVOReq attrVOReq) throws Exception, BusinessException {
        Assert.hasText(attrVOReq.getAttrName(), "属性名不能为空");
        attrService.add(DozerUtil.map(attrVOReq, AttrDTO.class));
        return new ResultModel().success();
    }

    /**
     * 查询属性名是否重复
     * @param attrName
     * @return
     * @throws Exception
     */
    @RequestMapping("findAttrName")
    public boolean findAttrName(String attrName) throws Exception {
        return attrService.findAttrName(attrName);
    }

    /**
     * 查询attr关联属性
     * @return
     * @throws Exception
     */
    @RequestMapping("list/{id}")
    @RequiresPermissions(AttrConstant.ATTR_VALUE_BTN)
    public ResultModel list(@PathVariable Integer id) throws Exception {
        List<AttrValueDTO> attrValueDTOList = attrService.findAttrValueById(id);
        return new ResultModel().success(DozerUtil.mapList(attrValueDTOList, AttrValueVOResp.class));
    }

    /**
     * 新增商品属性值名称查重
     * @param attrValue
     * @throws Exception
     */
    @RequestMapping("findAttrValueName")
    public boolean findAttrValueName(String attrValue) throws Exception {
        return attrService.findAttrValueName(attrValue);
    }

    /**
     * 商品属性新增
     * @param attrValueVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("insert")
    @RequiresPermissions(AttrConstant.ATTR_VALUE_INSERT_BTN)
    public ResultModel insert(AttrValueVOReq attrValueVOReq) throws Exception {
        Assert.hasText(attrValueVOReq.getAttrValue(), "属性值不能为空");
        attrService.insert(DozerUtil.map(attrValueVOReq, AttrValueDTO.class));
        return new ResultModel().success();
    }

    /**
     * 删除attr关联属性
     * @return
     * @throws Exception
     */
    @PostMapping("del/{id}")
    @RequiresPermissions(AttrConstant.ATTR_VALUE_DEL_BTN)
    public ResultModel del(@PathVariable Integer id) throws Exception {
        attrService.delAttrValueById(id);
        return new ResultModel().success(200);
    }
}
