package com.dj.mall.dict;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.dj.mall.dict.mapper")
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }

}
