package com.dj.mall.auth.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.entity.RoleResourceEntity;
import com.dj.mall.auth.mapper.role.RoleResourceMapper;
import com.dj.mall.auth.service.role.RoleResourceService;
import org.springframework.stereotype.Service;

@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResourceEntity> implements RoleResourceService {
}
