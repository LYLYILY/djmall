package com.dj.mall.dict.mapper.freight;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.entity.freight.FreightEntity;
import com.dj.mall.dict.mapper.bo.freight.FreightBO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface FreightMapper extends BaseMapper<FreightEntity> {

    List<FreightBO> freightShow() throws DataAccessException;
}
