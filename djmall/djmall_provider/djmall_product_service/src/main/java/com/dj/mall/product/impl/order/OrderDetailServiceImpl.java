package com.dj.mall.product.impl.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.order.OrderDetailService;
import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderDetailDTO;
import com.dj.mall.product.entity.order.OrderDetailEntity;
import com.dj.mall.product.mapper.order.OrderDetailMapper;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements OrderDetailService {

    /**
     * 新增订单详情
     *
     * @param mapList
     */
    @Override
    public void addDetail(List<OrderDetailDTO> mapList) throws Exception {
        super.saveBatch(DozerUtil.mapList(mapList, OrderDetailEntity.class));
    }

    /**
     * 订单详情table
     *
     * @param orderNo
     * @return
     */
    @Override
    public List<OrderDetailDTO> showOrderDetailTable(String orderNo) throws Exception {
        List<OrderDetailEntity> list = super.baseMapper.showOrderDetailTable(orderNo);
        return DozerUtil.mapList(list, OrderDetailDTO.class);
    }

    /**
     * 订单详情table
     *
     * @param orderNo
     * @return
     */
    @Override
    public List<OrderDetailDTO> showOrderDetailTable2(String orderNo) throws Exception {
        List<OrderDetailEntity> list = super.baseMapper.showOrderDetailTable2(orderNo);
        return DozerUtil.mapList(list, OrderDetailDTO.class);
    }

    @Override
    public List<OrderDetailDTO> findAll(OrderDTO orderDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_order_no", orderDTO.getOrderNo());
        List<OrderDetailEntity> list = super.list(queryWrapper);
        return DozerUtil.mapList(list,OrderDetailDTO.class);
    }
}
