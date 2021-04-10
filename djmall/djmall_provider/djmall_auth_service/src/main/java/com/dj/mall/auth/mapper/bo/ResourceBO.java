package com.dj.mall.auth.mapper.bo;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceBO implements Serializable {

    /**
     * 主键 资源ID
     */
    private Integer id;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 资源类型: 1.菜单, 2.按钮
     */
    private Integer resourceType;
}
