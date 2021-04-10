package com.dj.mall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.user.entity.UserRoleEntity;
import com.dj.mall.user.mapper.UserRoleMapper;
import com.dj.mall.user.service.user.UserRoleService;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
}
