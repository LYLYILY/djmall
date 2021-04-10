package com.dj.mall.admin.vo.auth.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserVOResp {

    private Integer id;

    private String userName;

    private String nikeName;

    private String userPhone;

    private String userEmail;

    private String userSex;

    private String userRank;

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
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 最终登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;


}
