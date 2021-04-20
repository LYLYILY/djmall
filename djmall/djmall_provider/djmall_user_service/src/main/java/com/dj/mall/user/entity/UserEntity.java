package com.dj.mall.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("djmall_auth_user")
public class UserEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String userPwd;

    private String nikeName;

    private String userPhone;

    private String userEmail;

    private String userSex;

    private String userStatus;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;

    /**
     * 加密用的盐
     */
    private String salt;
}
