package com.dj.mall.platform.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.platform.vo.order.OrderVOReq;
import com.dj.mall.product.api.order.OrderDetailService;
import com.dj.mall.product.api.order.OrderInfoService;
import com.dj.mall.product.api.order.OrderService;
import com.dj.mall.product.api.shop.ShopCarService;
import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderDetailDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Reference
    private OrderInfoService orderInfoService;

    @Reference
    private OrderDetailService orderDetailService;

    @Reference
    private ShopCarService shopCarService;

    /**
     * 提交订单
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("addOrder")
    public ResultModel addOrder(OrderVOReq orderVOReq) throws Exception {
        orderService.addOrder(DozerUtil.map(orderVOReq, OrderDTO.class));
        return new ResultModel().success();
    }

    /**
     * 主表展示
     * @param pageNo
     * @param orderStatus
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping("list")
    public ResultModel list(Integer pageNo, String orderStatus, Integer userId) throws Exception {
        PageResult page = orderService.list(pageNo, orderStatus, userId);
        return new ResultModel().success(PageResult.pageInfo(page.getCurrent(), page.getPages(), page.getRecords()));
    }

    /**
     * 子表展示
     * @param pageNo
     * @param orderStatus
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    public ResultModel show(Integer pageNo, String orderStatus, Integer userId) throws Exception {
        PageResult page = orderInfoService.show(pageNo, orderStatus, userId);
        return new ResultModel().success(PageResult.pageInfo(page.getCurrent(), page.getPages(), page.getRecords()));
    }

    /**
     * 订单详情table
     * @param orderNo
     * @return
     * @throws Exception
     */
    @PostMapping("showOrderDetailTable")
    public ResultModel showOrderDetailTable(String orderNo) throws Exception {
        List<OrderDetailDTO> list = orderDetailService.showOrderDetailTable(orderNo);
        return new ResultModel().success(list);
    }

    /**
     * 订单详情table2
     * @param orderNo
     * @return
     * @throws Exception
     */
    @PostMapping("showOrderDetailTable2")
    public ResultModel showOrderDetailTable2(String orderNo) throws Exception {
        List<OrderDetailDTO> list = orderDetailService.showOrderDetailTable2(orderNo);
        return new ResultModel().success(list);
    }

    /**
     * 去支付
     * @param orderNo
     * @return
     * @throws Exception
     */
    @PostMapping("payMoney")
    public ResultModel payMoney(String orderNo) throws Exception {
        orderService.updateOrderStatus(orderNo);
        return new ResultModel().success();
    }

    /**
     * 取消订单
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("quxiao")
    public ResultModel quxiao(OrderVOReq orderVOReq) throws Exception {
        orderService.quxiao(DozerUtil.map(orderVOReq, OrderDTO.class));
        return new ResultModel().success();
    }

    @PostMapping("again")
    public ResultModel again(String orderNo) throws Exception {

        return new ResultModel().success();
    }
}
