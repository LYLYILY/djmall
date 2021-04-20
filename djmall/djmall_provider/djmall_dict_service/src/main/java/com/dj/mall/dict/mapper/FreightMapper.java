package com.dj.mall.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.entity.FreightEntity;
import com.dj.mall.dict.mapper.bo.FreightBO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface FreightMapper extends BaseMapper<FreightEntity> {

    List<FreightBO> freightShow() throws DataAccessException;
}
