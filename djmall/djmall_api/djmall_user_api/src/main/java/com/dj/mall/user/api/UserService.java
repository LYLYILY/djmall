package com.dj.mall.user.api;


import com.dj.mall.user.dto.MenuDTO;
import com.dj.mall.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findByUserNameAndUserPwd(UserDTO userDTO) throws Exception;

    void add(UserDTO userDTO) throws Exception;

    boolean findUserName(String userName) throws Exception;

    boolean findUserEmail(String userEmail) throws Exception;

    boolean findUserPhone(String userPhone) throws Exception;

    List<UserDTO> findUserAll(UserDTO userDTO) throws Exception;

    void insertUserRole(UserDTO userDTO) throws Exception;

    Integer findRoleByUserId(Integer id);

    UserDTO findUserByUserId(Integer userId);

    void updateUserById(UserDTO userDTO);

    boolean findUserByName(Integer userId, String userName);

    boolean findUserByPhone(Integer userId, String userPhone);

    boolean findUserByEmail(Integer userId, String userEmail);

    List<MenuDTO> getLeft(Integer id) throws Exception;
}
