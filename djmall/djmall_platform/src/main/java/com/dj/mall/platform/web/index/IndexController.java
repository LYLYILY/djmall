package com.dj.mall.platform.web.index;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/index/")
public class IndexController {

    @Reference
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    


}
