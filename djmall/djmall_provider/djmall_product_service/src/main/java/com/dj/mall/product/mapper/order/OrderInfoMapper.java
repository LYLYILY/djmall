package com.dj.mall.product.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.entity.order.OrderInfoEntity;
import com.dj.mall.product.mapper.bo.OrderInfoBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;

public interface OrderInfoMapper extends BaseMapper<OrderInfoEntity> {

    IPage<OrderInfoBO> findOrderInfoAll(Page<Object> objectPage, @Param("orderStatus") String orderStatus, @Param("userId") Integer userId) throws DataAccessException;

    OrderInfoBO showDetails(String orderNo);

    void updateStatus(@Param("time") LocalDate time, @Param("status") String status);
}
