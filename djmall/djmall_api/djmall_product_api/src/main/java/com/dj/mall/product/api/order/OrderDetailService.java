package com.dj.mall.product.api.order;

import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    /**
     * 新增订单详情
     * @param mapList
     */
    void addDetail(List<OrderDetailDTO> mapList) throws Exception;

    /**
     * 订单详情table
     * @param orderNo
     * @return
     */
    List<OrderDetailDTO> showOrderDetailTable(String orderNo) throws Exception;

    List<OrderDetailDTO> findAll(OrderDTO orderDTO) throws Exception;

    /**
     * 订单详情table
     * @param orderNo
     * @return
     */
    List<OrderDetailDTO> showOrderDetailTable2(String orderNo) throws Exception;
}
