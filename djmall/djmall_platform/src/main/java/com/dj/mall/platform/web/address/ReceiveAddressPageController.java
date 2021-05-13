package com.dj.mall.platform.web.address;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.product.api.address.ReceiveAddressService;
import com.dj.mall.product.dto.address.ReceiveAddressDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/receiveAddress/")
public class ReceiveAddressPageController {

    @Reference
    private ReceiveAddressService receiveAddressService;

    /**
     *  个人中心-去添加地址
     */
    @GetMapping("toAddsAddress")
    public String toAddsAddress(){
        return "/shop/add_address";
    }

    /**
     *  确认订单去添加地址
     */
    @GetMapping("toAddAddress")
    public String toAddAddress(){
        return "/shop/address";
    }


    /**
     * 去修改
     * @param id
     * @param map
     * @return
     */
    @GetMapping("toUpdateAddress/{id}")
    public String toUpdateAddress(@PathVariable Integer id, ModelMap map) throws Exception {
        ReceiveAddressDTO receiveAddressDTO = receiveAddressService.findReceiveAddrById(id);
        map.put("address", receiveAddressDTO);
        return "/shop/upd";
    }
}
