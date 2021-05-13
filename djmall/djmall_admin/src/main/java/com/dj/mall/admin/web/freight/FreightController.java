package com.dj.mall.admin.web.freight;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.freight.FreightVOReq;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.DictConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.freight.FreightService;
import com.dj.mall.dict.dto.freight.FreightDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/freight/")
public class FreightController {

    @Reference
    private FreightService freightService;

    /**
     * 运费展示
     * @return
     */
    @PostMapping("show")
    @RequiresPermissions(DictConstant.COURIER_FEE)
    public ResultModel show() throws Exception {
        List<FreightDTO> freightDTOS = freightService.freightShow();
        return new ResultModel().success(freightDTOS);
    }

    /**
     * 运费新增
     * @param freightVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    @RequiresPermissions(DictConstant.FREIGHT_ADD_BTN)
    public ResultModel add(FreightVOReq freightVOReq) throws Exception {
        Assert.hasText(freightVOReq.getLogisticsCompany(), "物流公司不能为空");
        Assert.notNull(freightVOReq.getFreight(), "运费不能为空");
        freightService.add(DozerUtil.map(freightVOReq, FreightDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改运费
     */
    @RequiresPermissions("FREIGHT_UPDATE_BTN")
    @RequestMapping("update")
    public ResultModel update(FreightVOReq freightVOReq) throws Exception{
        Assert.notNull(freightVOReq.getFreight(), "运费不能为空");
        freightService.updateFreight(DozerUtil.map(freightVOReq, FreightDTO.class));
        return new ResultModel().success();
    }
}
