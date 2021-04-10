package com.dj.mall.admin.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index/")
public class IndexPageController {
    
    @GetMapping("toIndex")
    public String toIndex() {
        return "/index/index";
    }

    @GetMapping("toTop")
    public String toTop() {
        return "/index/top";
    }

    @GetMapping("toLeft")
    public String toLeft() {
        return "/index/left";
    }

    @GetMapping("toRight")
    public String toRight() {
        return "/index/right";
    }



}
