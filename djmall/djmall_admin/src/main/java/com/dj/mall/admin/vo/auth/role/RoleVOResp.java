package com.dj.mall.admin.vo.auth.role;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleVOResp implements Serializable {

    /**
     *  角色ID
     */
    private Integer id;

    /**
     *  角色名
     */
    private String roleName;
}
