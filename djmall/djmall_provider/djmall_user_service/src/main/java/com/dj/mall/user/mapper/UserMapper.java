package com.dj.mall.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.user.entity.UserEntity;
import com.dj.mall.user.mapper.bo.MenuBO;
import com.dj.mall.user.mapper.bo.UserBO;
import org.springframework.dao.DataAccessException;

import java.util.List;


public interface UserMapper extends BaseMapper<UserEntity> {

    List<UserBO> findUserAll(UserBO userBO) throws DataAccessException;

    List<MenuBO> getLeft(Integer id) throws DataAccessException;
}
