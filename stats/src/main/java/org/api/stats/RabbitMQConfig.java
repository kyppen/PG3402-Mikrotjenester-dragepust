package org.api.stats;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Bean
    public DirectExchange statsExchange() {
        return new DirectExchange("stats-exchange");
    }

    @Bean
    public Queue statsQueue() {
        return new Queue("stats-queue", true); // Durable queue
    }

    @Bean
    public Binding statsBinding(Queue statsQueue, DirectExchange statsExchange) {
        return BindingBuilder.bind(statsQueue).to(statsExchange).with("stats-routing-key");
    }
}

