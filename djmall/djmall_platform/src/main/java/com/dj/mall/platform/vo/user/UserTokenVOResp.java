package com.dj.mall.platform.vo.user;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
public class UserTokenVOResp {

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
