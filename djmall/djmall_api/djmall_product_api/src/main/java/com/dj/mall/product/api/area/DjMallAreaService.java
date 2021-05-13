package com.dj.mall.product.api.area;

import com.dj.mall.product.dto.area.DjMallAreaDTO;

import java.util.List;

public interface DjMallAreaService {

    /**
     * 展示地区
     */
    List<DjMallAreaDTO> findDjMallAreaAll() throws Exception;

    /**
     * 查询省份
     */
    List<DjMallAreaDTO> findAreaByPid(Integer pid) throws Exception;


}
