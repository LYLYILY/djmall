package com.dj.mall.admin.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
@DependsOn("shiroRealm")//该bean加载时依赖shiroRealm
public class ShiroConfiguration {

    /**
     * 自定义Realm
     */
    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * Shiro过滤器工厂
     *
     * @param securityManager
     * @return
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 定义 shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置默认登录的 URL，身份认证失败会访问该 URL
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        // 设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index/toIndex");
        // 设置未授权界面，权限认证失败会访问该 URL
        shiroFilterFactoryBean.setUnauthorizedUrl("/res/toError");
        Map<String, String> filters = new LinkedHashMap<>();
        filters.put("/user/login", "anon"); // anon 表示不需要认证
        filters.put("/user/toAdd", "anon");
        filters.put("/user/add", "anon");
        filters.put("/user/checkUserName", "anon");
        filters.put("/user/checkUserEmail", "anon");
        filters.put("/user/checkUserPhone", "anon");
        filters.put("/user/getSalt", "anon");
        filters.put("/user/updateStatus1/**", "anon");
        filters.put("/user/toRestPwd/**", "anon");
        filters.put("/user/restUserPwd", "anon");
        filters.put("/user/toForGetPwd", "anon");
        filters.put("/user/getVerifCode", "anon");
        filters.put("/user/updatePwd", "anon");
        filters.put("/user/getCode", "anon");
        filters.put("/static/**", "anon");
        filters.put("/**", "authc"); // authc 表示必须认证才可访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filters);
        return shiroFilterFactoryBean;
    }

}
