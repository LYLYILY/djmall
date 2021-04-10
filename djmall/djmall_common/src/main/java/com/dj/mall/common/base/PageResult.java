package com.dj.mall.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象封装
 */
@Data
@Builder // builder模式
@NoArgsConstructor // 无参构造
@AllArgsConstructor // 全参构造
public class PageResult implements Serializable {

    /**
     * 当前页
     */
    private Long current = 1l;

    /**
     * 总页数
     */
    private Long pages = 1l;

    /**
     * 数据
     */
    private List<?> records;

    /**
     * 分页信息
     * @param current 当前页
     * @param pages 总页数
     * @param records 分页结果
     * @return
     */
    public static PageResult pageInfo(Long current, Long pages, List<?> records) {
        return PageResult.builder().current(current).pages(pages).records(records).build();
    }

}
