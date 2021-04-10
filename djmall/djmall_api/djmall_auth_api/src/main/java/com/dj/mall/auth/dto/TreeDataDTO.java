package com.dj.mall.auth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TreeDataDTO implements Serializable {

    /**
     *  treeId
     */
    private Integer id;

    /**
     *  treePid
     */
    private Integer parentId;

    /**
     *  treeName
     */
    private String name;

    /**
     *  是否展开
     */
    private boolean open = true;

    /**
     *  是否选中
     */
    private boolean checked = false;
}
