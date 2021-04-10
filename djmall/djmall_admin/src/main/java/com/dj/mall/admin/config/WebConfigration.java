package com.dj.mall.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/user/toLogin");
        interceptorRegistration.excludePathPatterns("/user/login");
        interceptorRegistration.excludePathPatterns("/user/add");
        interceptorRegistration.excludePathPatterns("/user/toAdd");
        interceptorRegistration.excludePathPatterns("/user/checkUserName");
        interceptorRegistration.excludePathPatterns("/user/checkUserEmail");
        interceptorRegistration.excludePathPatterns("/user/checkUserPhone");
        interceptorRegistration.excludePathPatterns("/static/**");

        InterceptorRegistration interceptorRegistration1 = registry.addInterceptor(authInterceptor);
        interceptorRegistration1.addPathPatterns("/user/toShow");
        interceptorRegistration1.addPathPatterns("/res/toShow");
        interceptorRegistration1.addPathPatterns("/role/toShow");
        interceptorRegistration1.addPathPatterns("/dict/toShow");
    }
}
