package com.dj.mall.product.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.product.entity.order.OrderDetailEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity> {

    List<OrderDetailEntity> showOrderDetailTable(String orderNo) throws DataAccessException;

    List<OrderDetailEntity> showOrderDetailTable2(String orderNo) throws DataAccessException;
}
