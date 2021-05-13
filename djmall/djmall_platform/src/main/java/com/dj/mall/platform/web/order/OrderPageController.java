package com.dj.mall.platform.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.product.api.order.OrderInfoService;
import com.dj.mall.product.api.order.OrderService;
import com.dj.mall.product.dto.order.OrderDTO;
import com.dj.mall.product.dto.order.OrderInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order/")
public class OrderPageController {

    @Reference
    private OrderInfoService orderInfoService;

    @Reference
    private OrderService orderService;

    @GetMapping("toShow")
    public String toShow() {
        return "/order/show";
    }

    /**
     * 去子表订单详情
     * @param orderNo
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping("toShowDetails/{orderNo}")
    public String toShowDetails(@PathVariable String orderNo, ModelMap map) throws Exception {
        OrderInfoDTO orderInfoDTO = orderInfoService.showDetails(orderNo);
        map.put("orderInfoDTO", orderInfoDTO);
        return "/order/show_detail";
    }

    @GetMapping("toShowDetails2/{orderNo}")
    public String toShowDetails2(@PathVariable String orderNo, ModelMap map) throws Exception {
        OrderDTO orderDTO = orderService.showDetails2(orderNo);
        map.put("orderDTO", orderDTO);
        return "/order/show_detail2";
    }

}
