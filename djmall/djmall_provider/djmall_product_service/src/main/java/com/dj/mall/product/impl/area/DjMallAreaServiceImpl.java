package com.dj.mall.product.impl.area;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.area.DjMallAreaService;
import com.dj.mall.product.dto.area.DjMallAreaDTO;
import com.dj.mall.product.entity.area.DjMallAreaEntity;
import com.dj.mall.product.mapper.area.DjMallAreaMapper;


import java.util.List;

@Service
public class DjMallAreaServiceImpl extends ServiceImpl<DjMallAreaMapper, DjMallAreaEntity> implements DjMallAreaService {

    /**
     * @return
     * 展示全部地区
     */
    @Override
    public List<DjMallAreaDTO> findDjMallAreaAll() throws Exception {
        List<DjMallAreaEntity> djMallArealist = super.list();
        return DozerUtil.mapList(djMallArealist, DjMallAreaDTO.class);
    }

    /**
     * 查询省份
     */
    @Override
    public List<DjMallAreaDTO> findAreaByPid(Integer pid) throws Exception {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("area_parent_id", pid);
        List list = super.list(queryWrapper);
        return list;
    }

}
