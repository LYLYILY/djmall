package com.dj.mall.cmpt;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableDubbo
@MapperScan("com.dj.mall.cmpt.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CmptApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmptApplication.class, args);
    }
}
