package com.dj.mall.user.api;


import com.dj.mall.common.base.BusinessException;
import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    /**
     * 登录
     * @param userDTO
     * @return
     * @throws Exception
     */
    UserDTO findByUserNameAndUserPwd(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 注册
     * @param userDTO
     * @throws Exception
     */
    void add(UserDTO userDTO) throws Exception;

    /**
     * 用户注册-用户名查重
     * @param userName
     * @return
     * @throws Exception
     */
    boolean findUserName(String userName) throws Exception;

    /**
     * 用户注册-邮箱查重
     * @param userEmail
     * @return
     * @throws Exception
     */
    boolean findUserEmail(String userEmail) throws Exception;

    /**
     * 用户注册-手机号查重
     * @param userPhone
     * @return
     * @throws Exception
     */
    boolean findUserPhone(String userPhone) throws Exception;

    /**
     * 用户管理展示
     * @param userDTO
     * @return
     * @throws Exception
     */
    List<UserDTO> findUserAll(UserDTO userDTO) throws Exception;

    /**
     * 用户授权
     * @param userDTO
     * @throws Exception
     */
    void insertUserRole(UserDTO userDTO) throws Exception;

    /**
     * 根据用户id查找角色
     * @param id
     * @return
     * @throws Exception
     */
    Integer findRoleByUserId(Integer id) throws Exception;

    /**
     * 修改用户名查重
     * @param userId
     * @return
     * @throws Exception
     */
    UserDTO findUserByUserId(Integer userId) throws Exception;

    /**
     * 用户修改
     * @param userDTO
     * @throws Exception
     */
    void updateUserById(UserDTO userDTO) throws Exception;

    /**
     * 修改用户名查重
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    boolean findUserByName(Integer userId, String userName) throws Exception;

    /**
     * 修改手机号重复
     * @param userId
     * @param userPhone
     * @return
     * @throws Exception
     */
    boolean findUserByPhone(Integer userId, String userPhone) throws Exception;

    /**
     * 修改邮箱重复
     * @param userId
     * @param userEmail
     * @return
     * @throws Exception
     */
    boolean findUserByEmail(Integer userId, String userEmail) throws Exception;

    /**
     * 获取左侧菜单
     * @param id
     * @return
     * @throws Exception
     */
    List<MenuDTO> getLeft(Integer id) throws Exception;

    /**
     * 获取盐
     * @param queryName
     * @return
     * @throws BusinessException
     */
    String findSaltByQueryName(String queryName) throws BusinessException;

    /**
     * 激活用户
     * @param userDTO
     * @throws Exception
     */
    void updateUserStatusById(UserDTO userDTO) throws Exception;
}
