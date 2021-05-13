package com.dj.mall.product.api.order;

import com.dj.mall.common.base.PageResult;
import com.dj.mall.product.dto.order.OrderDTO;

import java.time.LocalDate;

public interface OrderService {

    /**
     * 提交订单
     * @param orderDTO
     * @throws Exception
     */
    void addOrder(OrderDTO orderDTO) throws Exception;

    /**
     * 主表展示
     * @param pageNo
     * @param orderStatus
     * @param userId
     * @return
     */
    PageResult list(Integer pageNo, String orderStatus, Integer userId) throws Exception;

    /**
     * 去支付
     * @param orderNo
     */
    void updateOrderStatus(String orderNo) throws Exception;

    /**
     * 取消订单
     * @param orderDTO
     * @throws Exception
     */
    void quxiao(OrderDTO orderDTO) throws Exception;

    /**
     * 主表订单详情
     * @param orderNo
     * @return
     */
    OrderDTO showDetails2(String orderNo) throws Exception;

    /**
     * 根据订单号查找状态
     * @param orderNo
     * @return
     */
    OrderDTO findStatusByOrderNo(String orderNo) throws Exception;

    void wancheng(LocalDate time) throws Exception;
}
