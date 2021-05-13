package com.dj.mall.product.api.order;

import com.dj.mall.common.base.PageResult;
import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderInfoDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrderInfoService {


    /**
     * 新增子订单
     * @param mapList
     */
    void addOrderInfo(List<OrderInfoDTO> mapList) throws Exception;

    /**
     * 子表展示
     * @param pageNo
     * @param orderStatus
     * @param userId
     * @return
     */
    PageResult show(Integer pageNo, String orderStatus, Integer userId) throws Exception;

    /**
     * 订单详情
     * @param orderNo
     * @return
     */
    OrderInfoDTO showDetails(String orderNo) throws Exception;

    /**
     * 去支付
     * @param orderNo
     * @throws Exception
     */
    void updateOrderStatus(String orderNo) throws Exception;

    /**
     * 修改子表状态为取消
     * @param orderDTO
     */
    void updateOrderStatus2(OrderDTO orderDTO) throws Exception;

    void updateStatus(LocalDate time, String status) throws Exception;
}
