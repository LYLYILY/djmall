package com.dj.mall.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单播-配置
 */
@Configuration
public class EmailRabbitConfig {

    /**
     * 创建队列-队列1
     *
     * @return
     */
    @Bean
    public Queue directQueue1() {
        // name=队列的名称
        // durable 是否持久化
        // exclusive 是否独占队列
        // autoDelete 是否自动删除
        return new Queue("directQueue1", true);
    }

    /**
     * 创建队列-队列2
     *
     * @return
     */
    @Bean
    public Queue directQueue2() {
        return new Queue("directQueue2", true);
    }

    /**
     * 创建单播路由
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }

    /**
     * 队列绑定1
     *
     * @return
     */
    @Bean
    public Binding bindingDirectQueue1() {
        // Message的routingKey与其一致 单播建议与队列名称一致
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("directQueue1");
    }

    /**
     * 队列绑定2
     *
     * @return
     */
    @Bean
    public Binding bindingDirectQueue2() {
        // Message的routingKey与其一致
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("directQueue2");
    }
}
