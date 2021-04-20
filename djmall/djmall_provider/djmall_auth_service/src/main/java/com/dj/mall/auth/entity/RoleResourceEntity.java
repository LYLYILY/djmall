package com.dj.mall.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_role_resource")
public class RoleResourceEntity {

    /**
     *  角色ID
     */
    private Integer roleId;

    /**
     *  资源ID
     */
    private Integer resourceId;
}
