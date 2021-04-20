package com.dj.mall.auth.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.entity.ResourceEntity;
import com.dj.mall.auth.entity.RoleEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleEntity> {


    List<ResourceEntity> findRoleReourceByRoleId(Integer roleId) throws DataAccessException;
}
