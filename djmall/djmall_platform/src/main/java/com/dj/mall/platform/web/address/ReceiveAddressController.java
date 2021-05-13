package com.dj.mall.platform.web.address;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.platform.vo.address.ReceiveAddressVOReq;
import com.dj.mall.platform.vo.address.ReceiveAddressVOResp;
import com.dj.mall.product.api.address.ReceiveAddressService;
import com.dj.mall.product.dto.address.ReceiveAddressDTO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/receiveAddress/")
public class ReceiveAddressController {

    @Reference
    private ReceiveAddressService receiveAddressService;

    /**
     *  展示收货地址
     */
    @PostMapping("showReceiveAddress")
    public ResultModel showReceiveAddress(Integer userId) throws Exception{
        List<ReceiveAddressDTO> addressList = receiveAddressService.showReceiveAddressByUserId(userId);
        return new ResultModel<>().success(DozerUtil.mapList(addressList, ReceiveAddressVOResp.class));
    }

    /**
     * 新增收货地址
     * @param receiveAddressVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("addAddress")
    public ResultModel addAddress(ReceiveAddressVOReq receiveAddressVOReq) throws Exception {
        Assert.hasText(receiveAddressVOReq.getAddressee(), "收件人不能为空");
        Assert.hasText(receiveAddressVOReq.getPhone(), "手机号不能为空");
        Assert.hasText(receiveAddressVOReq.getUserProvince(), "省不能为空");
        Assert.hasText(receiveAddressVOReq.getUserCity(), "市不能为空");
        Assert.hasText(receiveAddressVOReq.getUserDistrict(), "县不能为空");
        Assert.hasText(receiveAddressVOReq.getAddress(), "省不能为空");
        receiveAddressService.addAddress(DozerUtil.map(receiveAddressVOReq, ReceiveAddressDTO.class));
        return new ResultModel().success();
    }

    /**
     * 修改收货地址
     * @param receiveAddressVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateAddress")
    public ResultModel updateAddress(ReceiveAddressVOReq receiveAddressVOReq) throws Exception {
        Assert.hasText(receiveAddressVOReq.getAddressee(), "收件人不能为空");
        Assert.hasText(receiveAddressVOReq.getPhone(), "手机号不能为空");
        Assert.hasText(receiveAddressVOReq.getUserProvince(), "省不能为空");
        Assert.hasText(receiveAddressVOReq.getUserCity(), "市不能为空");
        Assert.hasText(receiveAddressVOReq.getUserDistrict(), "县不能为空");
        Assert.hasText(receiveAddressVOReq.getAddress(), "省不能为空");
        receiveAddressService.updateAddress(DozerUtil.map(receiveAddressVOReq, ReceiveAddressDTO.class));
        return new ResultModel().success();
    }

    /**
     * 收货地址删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("delAddress")
    public ResultModel delAddress(Integer id) throws Exception {
        receiveAddressService.delAddressByUserId(id);
        return new ResultModel().success();
    }

}
