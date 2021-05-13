package com.dj.mall.platform.vo.user;

import lombok.Data;

@Data
public class UserTokenVOReq {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 性别
     */
    private String userSex;

    /**
     * email
     */
    private String userEmail;

    /**
     * 用户级别
     */
    private String userRank;

    /**
     * 用户昵称
     */
    private String nikeName;

    /**
     *  状态
     */
    private String userStatus;

    /**
     *账号、手机号、邮箱
     */
    private String queryName;

    /**
     *  模糊查询-用户名-邮箱-手机号
     */
    private String queryNameOrPhoneOrEmail;

    /**
     *  查询级别
     */
    private Integer queryRank;

    /**
     * 加密用的盐
     */
    private String salt;

    /**
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 用户头像
     */
    private String userPortrait;
}
