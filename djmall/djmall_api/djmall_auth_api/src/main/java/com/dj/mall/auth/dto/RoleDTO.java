package com.dj.mall.auth.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable {

    /**
     *  角色ID
     */
    private Integer id;

    /**
     *  角色名
     */
    private String roleName;

    /**
     *  资源IDs
     */
    private List<Integer> ids;

    /**
     *  用户IDs
     */
    private List<Integer> userIds;
}
