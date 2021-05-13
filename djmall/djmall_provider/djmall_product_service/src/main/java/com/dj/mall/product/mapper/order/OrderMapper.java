package com.dj.mall.product.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.dto.order.OrderInfoDTO;
import com.dj.mall.product.entity.order.OrderEntity;
import com.dj.mall.product.mapper.bo.OrderBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;

public interface OrderMapper extends BaseMapper<OrderEntity> {

    IPage<OrderBO> findOrderAll(Page<Object> objectPage, @Param("orderStatus") String orderStatus, @Param("userId") Integer userId) throws DataAccessException;


    OrderBO showDetails2(String orderNo) throws DataAccessException;

    void updateStatus(@Param("time") LocalDate time, @Param("status") String status) throws DataAccessException;
}
