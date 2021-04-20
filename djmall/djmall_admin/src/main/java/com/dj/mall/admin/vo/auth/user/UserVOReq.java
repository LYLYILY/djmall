package com.dj.mall.admin.vo.auth.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserVOReq {

    private Integer id;

    private String userName;

    private String userPwd;

    private String userPhone;

    private String userSex;

    private String userEmail;

    private String userRank;

    private String nikeName;

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
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 最终登陆时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 加密用的盐
     */
    private String salt;
}
