package com.dj.mall.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("djmall_auth_user_role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {

    /**
     *  用户ID
     */
    private Integer userId;

    /**
     *  角色ID
     */
    private Integer roleId;

}
