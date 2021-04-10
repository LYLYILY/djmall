package com.dj.mall.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private Integer id;

    private String userName;

    private String userPwd;

    private String userPhone;

    private String userSex;

    private String userEmail;

    private String nikeName;

    private String userRank;

    private String userStatus;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 最终登陆时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 三者得大名
     */
    private String queryName;

    /**
     *  性别展示
     */
    private String userSexShow;

    /**
     *  状态展示
     */
    private String userStatusShow;

    /**
     *  模糊查询-用户名-邮箱-手机号
     */
    private String queryNameOrPhoneOrEmail;

    /**
     *  查询级别
     */
    private Integer queryRank;

    /**
     * 角色id
     */
    private Integer roleId;

    List<MenuDTO> resourceList;

}
