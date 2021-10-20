package io.github.syakuis.producer.push.infrastucture.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-05-21
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class PushRabbitConfiguration {
    private final MessageConverter messageConverter;
    public static final String EXCHANGE_NAME = "exchange.push";
    public static final String QUEUE_NAME = "queue.push";

    @Bean
    DirectExchange pushExchange() {
        return ExchangeBuilder
            .directExchange(EXCHANGE_NAME)
            .build();
    }

    @Bean
    Queue pushQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    Binding pushBinding() {
        return BindingBuilder.bind(pushQueue()).to(pushExchange()).withQueueName();
    }

    @Bean
    AmqpTemplate pushRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        return rabbitTemplate;
    }
}