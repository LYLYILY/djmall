package com.dj.mall.admin.vo.auth.role;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleVOReq implements Serializable {

    /**
     *  @author: ly
     *  角色ID
     */
    private Integer id;

    /**
     *  @author: ly
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
