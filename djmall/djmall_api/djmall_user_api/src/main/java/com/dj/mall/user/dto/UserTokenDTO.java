package com.dj.mall.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserTokenDTO implements Serializable {

    /**
    * token
    */
    private String token;

    /**
     * 用户名
     */
    private String queryName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String userPwd;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户头像
     */
    private String userPortrait;
}
