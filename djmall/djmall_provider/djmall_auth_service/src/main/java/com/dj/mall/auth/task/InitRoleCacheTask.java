package com.dj.mall.auth.task;

import com.dj.mall.auth.api.role.RoleAuthService;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.common.constant.CacheKeyConsant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class InitRoleCacheTask {

    @Autowired
    private RoleAuthService roleAuthService;

    @Autowired
    private RedisTemplate redisTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public void init(){
        log.info("=====================init start============================");
        //查询全部的角色ID
        try {
            List<RoleDTO> role = roleAuthService.findRoleAll();
            HashOperations hashOperations = redisTemplate.opsForHash();
            //根据角色ID查询资源
            for (RoleDTO roleDTO : role) {
                List<ResourceDTO> resourceDTOS = roleAuthService.getRoleResource(roleDTO.getId());
                resourceDTOS.forEach(resource -> {
                    //存入到redis
                    hashOperations.put(CacheKeyConsant.ROLE_ + roleDTO.getId(), CacheKeyConsant.RESOURCE_ + resource.getId(), resource);
                });
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        log.info("=====================init end============================");
    }






}
