package com.dj.mall.admin.config;

import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.common.constant.UserConstant;
import com.dj.mall.user.dto.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建简单授权信息
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        UserDTO userDTO = (UserDTO) SecurityUtils.getSubject().getSession().getAttribute(UserConstant.USER);
        /*List<String> list = userDTO.getResourceList().stream().map(MenuDTO::getResourceCode).collect(Collectors.toList());*/
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<ResourceDTO> list = hashOperations.values("ROLE_" + userDTO.getRoleId());
        List<String> list1 = list.stream().map(ResourceDTO::getResourceCode).collect(Collectors.toList());
        simpleAuthorInfo.addStringPermissions(list1);
        return simpleAuthorInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 得到用户名
        String username = (String) authenticationToken.getPrincipal();
        // 得到密码
        String password = new String((char[]) authenticationToken.getCredentials());
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
