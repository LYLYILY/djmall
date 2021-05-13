package com.dj.mall.product.impl.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.order.OrderInfoService;
import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderInfoDTO;
import com.dj.mall.product.entity.order.OrderInfoEntity;
import com.dj.mall.product.mapper.order.OrderInfoMapper;
import com.dj.mall.product.mapper.bo.OrderInfoBO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfoEntity> implements OrderInfoService {

    /**
     * 新增子订单
     *
     * @param mapList
     */
    @Override
    public void addOrderInfo(List<OrderInfoDTO> mapList) {
        super.saveBatch(DozerUtil.mapList(mapList, OrderInfoEntity.class));
    }

    /**
     * 子表展示
     *
     * @param pageNo
     * @param orderStatus
     * @param userId
     * @return
     */
    @Override
    public PageResult show(Integer pageNo, String orderStatus, Integer userId) throws Exception {
        IPage<OrderInfoBO> iPage = super.baseMapper.findOrderInfoAll(new Page<>(pageNo, 5), orderStatus, userId);
        return PageResult.pageInfo(iPage.getCurrent(),iPage.getPages(), iPage.getRecords());
    }

    /**
     * 订单详情
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfoDTO showDetails(String orderNo) {
        OrderInfoBO orderInfoBO = super.baseMapper.showDetails(orderNo);
        return DozerUtil.map(orderInfoBO, OrderInfoDTO.class);
    }

    /**
     * 去支付
     * @param orderNo
     */
    @Override
    public void updateOrderStatus(String orderNo) {
        UpdateWrapper queryWrapper = new UpdateWrapper();
        queryWrapper.eq("parent_order_no", orderNo);
        queryWrapper.set("order_status", "ORDER_WAIT_RECE");
        queryWrapper.set("update_time", LocalDateTime.now());
        super.update(queryWrapper);
    }

    /**
     * 修改子表状态为取消
     * @param orderDTO
     */
    @Override
    public void updateOrderStatus2(OrderDTO orderDTO) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("order_status", orderDTO.getOrderStatus());
        updateWrapper.eq("parent_order_no", orderDTO.getOrderNo());
        updateWrapper.set("update_time", LocalDateTime.now());
        super.update(updateWrapper);
    }

    @Override
    public void updateStatus(LocalDate time, String status) throws Exception {
        super.baseMapper.updateStatus(time, status);
    }
}
