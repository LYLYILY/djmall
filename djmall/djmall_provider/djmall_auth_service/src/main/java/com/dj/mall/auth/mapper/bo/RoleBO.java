package com.dj.mall.auth.mapper.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RoleBO implements Serializable {

    /**
     *  角色ID
     */
    private Integer id;

    /**
     *  角色名
     */
    private String roleName;
}
