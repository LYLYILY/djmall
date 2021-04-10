package com.dj.mall.admin.config;

import com.dj.mall.user.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (null != session){
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (null != user){
                return true;
            }
        }
        response.sendRedirect(request.getContextPath()+"/user/toLogin");
        return false;
    }
}
