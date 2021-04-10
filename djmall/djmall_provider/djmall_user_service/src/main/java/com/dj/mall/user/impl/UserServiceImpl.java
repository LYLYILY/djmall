package com.dj.mall.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.dto.ResourceDTO;
import com.dj.mall.auth.dto.RoleDTO;
import com.dj.mall.auth.mapper.bo.ResourceBO;
import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.entity.UserRoleEntity;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.user.api.UserService;
import com.dj.mall.user.dto.UserDTO;
import com.dj.mall.user.entity.UserEntity;
import com.dj.mall.user.mapper.UserMapper;
import com.dj.mall.user.mapper.bo.MenuBO;
import com.dj.mall.user.mapper.bo.UserBO;
import com.dj.mall.user.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDTO findByUserNameAndUserPwd(UserDTO userDTO) throws Exception {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userDTO.getQueryName())
                .or().eq("user_phone", userDTO.getQueryName())
                .or().eq("user_email", userDTO.getQueryName());
        UserEntity userEntity = super.baseMapper.selectOne(queryWrapper);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.set("last_login_time", LocalDateTime.now());
        updateWrapper.eq("id", userEntity.getId());
        super.update(updateWrapper);
        if (userEntity == null) {
            throw new BusinessException("用户名输入错误");
        }
        if (!userEntity.getUserPwd().equals(userDTO.getUserPwd())) {
            throw new BusinessException("密码输入错误");
        }
        UserDTO userDTO1 = DozerUtil.map(userEntity, UserDTO.class);
        userDTO1.setResourceList(this.getLeft(userEntity.getId()));
        return userDTO1;
    }

    @Override
    public void add(UserDTO userDTO) throws Exception {
        userDTO.setCreateTime(LocalDateTime.now());
        UserEntity userEntity = DozerUtil.map(userDTO, UserEntity.class);
        super.save(userEntity);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getId());
        userRoleEntity.setRoleId(Integer.parseInt(userDTO.getUserRank()));
        userRoleService.save(userRoleEntity);
    }

    @Override
    public boolean findUserName(String userName) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        UserEntity userEntity = super.getOne(wrapper);
        if (null != userEntity) {
            return false;
        }
        return true;
    }

    @Override
    public boolean findUserEmail(String userEmail) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_email", userEmail);
        UserEntity userEntity = super.getOne(wrapper);
        if (null != userEntity) {
            return false;
        }
        return true;
    }

    @Override
    public boolean findUserPhone(String userPhone) throws Exception {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone", userPhone);
        UserEntity userEntity = super.getOne(wrapper);
        if (null != userEntity) {
            return false;
        }
        return true;
    }

    @Override
    public List<UserDTO> findUserAll(UserDTO userDTO) throws Exception {
        List<UserBO> userBOList = super.baseMapper.findUserAll(DozerUtil.map(userDTO, UserBO.class));
        return DozerUtil.mapList(userBOList, UserDTO.class);
    }

    @Override
    public void insertUserRole(UserDTO userDTO) throws Exception {
        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userDTO.getId());
        UserRoleEntity one = userRoleService.getOne(queryWrapper);
        /*if (null == one){
            userRoleService.save(UserRoleEntity.builder()
                    .userId(userDTO.getId())
                    .roleId(userDTO.getRoleId())
                    .build()
            );
        } else {*/
        if(one != null) {
            one.setRoleId(userDTO.getRoleId());
            userRoleService.update(one, queryWrapper);
        }

        /*}*/
    }

    @Override
    public Integer findRoleByUserId(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        UserRoleEntity one = userRoleService.getOne(queryWrapper);
        return one.getRoleId();
    }

    @Override
    public UserDTO findUserByUserId(Integer userId) {
        UserEntity userEntity = super.getById(userId);
        return DozerUtil.map(userEntity, UserDTO.class);
    }

    @Override
    public void updateUserById(UserDTO userDTO) {
        super.updateById(DozerUtil.map(userDTO, UserEntity.class));
    }

    /**
     * 修改查重
     * @param userId
     * @param userName
     * @return
     */
    @Override
    public boolean findUserByName(Integer userId, String userName) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        UserEntity userEntity = super.getOne(queryWrapper);
        if (null != userEntity && !userEntity.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 修改查重
     * @param userId
     * @param userPhone
     * @return
     */
    @Override
    public boolean findUserByPhone(Integer userId, String userPhone) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", userPhone);
        UserEntity userEntity = super.getOne(queryWrapper);
        if (null != userEntity && !userEntity.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 修改查重
     * @param userId
     * @param userEmail
     * @return
     */
    @Override
    public boolean findUserByEmail(Integer userId, String userEmail) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        UserEntity userEntity = super.getOne(queryWrapper);
        if (null != userEntity && !userEntity.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    @Override
    public List<MenuDTO> getLeft(Integer id) throws Exception {
        List<MenuBO> resourceBOS = baseMapper.getLeft(id);
        return DozerUtil.mapList(resourceBOS, MenuDTO.class);
    }
}
