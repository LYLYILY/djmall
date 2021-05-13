package com.dj.mall.product.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dj.mall.product.api.order.OrderService;
import com.dj.mall.product.dto.order.OrderDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    /**
     * 消费者
     *
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "delay-queue")
    public void process(Message message) throws Exception {
        String str = new String(message.getBody(), "UTF-8");
        JSONObject data = JSON.parseObject(str);
        String orderNo = data.getString("orderNo");
        OrderDTO orderDTO = orderService.findStatusByOrderNo(orderNo);
        if (orderDTO.getOrderStatus().equals("ORDER_OBLIGATION")) {
            orderService.quxiao(OrderDTO.builder().orderNo(data.getString("orderNo")).orderStatus("ORDER_CANCELED").build());
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void timedTask() throws Exception {
        LocalDate localDate = LocalDate.now();
        LocalDate time = localDate.minusDays(15);
        orderService.wancheng(time);
    }
}
