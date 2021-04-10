package com.dj.mall.admin.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.auth.api.resource.ResourceService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //当前请求
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        //用户访问权限路径
        UserDTO user = (UserDTO) request.getSession().getAttribute("USER");
        List<MenuDTO> list = user.getResourceList();//userService.getLeft(user.getId());
        /*for (MenuDTO resourceDTO : list) {
            if (uri.equals(ctx + resourceDTO.getUrl())){
                return true;
            }
        }*/
        if (list.stream().filter(menu -> uri.equals(ctx + menu.getUrl())).findAny().isPresent()) {
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/res/toError");
        return false;
    }
}
