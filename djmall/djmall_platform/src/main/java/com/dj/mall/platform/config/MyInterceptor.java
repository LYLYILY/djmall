package com.dj.mall.platform.config;


import com.dj.mall.common.constant.CacheKeyConsant;
import com.dj.mall.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "TOKEN";

    @Autowired
    private RedisTemplate redisTemplate;

    private boolean checkToken(String token) {
        if (null != token) {
            // token 有效性验证
            UserDTO userDTOResp = (UserDTO) redisTemplate.opsForValue().get(CacheKeyConsant.USER_TOKEN + token);
            return !(userDTOResp == null);
        }
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 是ajax请求 从Harder中获取Token信息
        if (httpServletRequest.getHeader("x-requested-with") != null && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            // 从request的heard中获取token
            String token = httpServletRequest.getHeader(TOKEN);
            if (checkToken(token)) {
                return true;
            }
            // 状态码随意 但是不能是Http预设的状态码
            httpServletResponse.setStatus(666);
            return false;
        } else {
            // 获取请求中的token
            String token = httpServletRequest.getParameter(TOKEN);
            if (checkToken(token)) {
                return true;
            }
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");
            return false;
        }
    }
}
