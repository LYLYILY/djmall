package com.dj.mall.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OrderRabbitConfig {

    /**
     * 延时队列
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return new Queue("delay-queue", true);
    }

    /**
     * 延时队列交换机
     * @return
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //属性参数 交换机名称 交换机类型 是否持久化 是否自动删除 配置参数
        return new CustomExchange("delay-exchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingDelayQueue() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay-queue").noargs();
    }
}
