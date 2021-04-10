package com.dj.mall.user.mapper.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserBO {

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
     * 昵称
     */
    private String nikeName;

    /**
     * 级别
     */
    private String userRank;

    /**
     * 状态
     */
    private String userStatus;

    /**
     *  性别展示
     */
    private String userSexShow;

    /**
     *  状态展示
     */
    private String userStatusShow;

    /**
     *  查询级别
     */
    private Integer queryRank;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 最终登陆时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    /**
     *  模糊查询-用户名-邮箱-手机号
     */
    private String queryNameOrPhoneOrEmail;

}
