package com.dj.mall.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_role_resource")
public class RoleResourceEntity {

    /**
     *
     *  @Date: 2021/1/17 16:39
     *  角色ID
     */
    private Integer roleId;

    /**
     *
     *  @Date: 2021/1/17 16:39
     *  资源ID
     */
    private Integer resourceId;
}
