package com.dj.mall.platform.web.shop;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.dict.api.data.DictDataService;
import com.dj.mall.dict.dto.data.DictDataDTO;
import com.dj.mall.product.api.address.ReceiveAddressService;
import com.dj.mall.product.dto.address.ReceiveAddressDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/shop/")
public class ShopPageController {


    @Reference
    private DictDataService dictDataService;

    @Reference
    private ReceiveAddressService receiveAddressService;

    /**
     *  去购物车
     */
    @GetMapping("toShopCar")
    public String toShopCar() {
        return "/shop/shop_car";
    }

    @GetMapping("toSettle/{userId}")
    public String toSettle(@PathVariable Integer userId, ModelMap map) throws Exception {
        List<DictDataDTO> pay = dictDataService.findDictNameByParentCode("PAYMENT_METHOD");
        List<ReceiveAddressDTO> address = receiveAddressService.findAddressByUserId(userId);
        map.put("pay", pay);
        map.put("address", address);
        return "/shop/settle";
    }




}
