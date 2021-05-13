package com.dj.mall.platform.web.area;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address/")
public class DjMallAreaPageController {

    /**
     *  收获地址
     */
    @GetMapping("toAddress")
    public String toAddress(){
        return "/shop/address";
    }







}
