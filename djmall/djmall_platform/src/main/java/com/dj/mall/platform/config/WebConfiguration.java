package com.dj.mall.platform.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class WebConfiguration implements WebMvcConfigurer {

    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(myInterceptor);
        interceptorRegistration.addPathPatterns("/**");
//        interceptorRegistration.excludePathPatterns("/user/toLogin");
//        interceptorRegistration.excludePathPatterns("/user/login");
//        interceptorRegistration.excludePathPatterns("/user/register");
//        interceptorRegistration.excludePathPatterns("/user/toRegister");
        interceptorRegistration.excludePathPatterns("/static/**");

    }

}
