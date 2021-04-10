package com.dj.mall.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_resource")
public class ResourceEntity {

    /**
     * 主键 资源ID
     */
    @TableId(type = IdType.AUTO)
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
