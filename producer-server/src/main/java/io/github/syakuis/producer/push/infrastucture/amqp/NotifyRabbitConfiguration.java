package io.github.syakuis.producer.push.infrastucture.amqp;

import io.github.syakuis.producer.push.model.NotifyMessageProperties;
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
public class NotifyRabbitConfiguration {
    private final MessageConverter messageConverter;

    @Bean
    FanoutExchange notifyExchange() {
        return ExchangeBuilder
            .fanoutExchange(NotifyMessageProperties.EXCHANGE_NAME)
            .build();
    }

    @Bean
    Queue notifyQueue() {
        return QueueBuilder.durable(NotifyMessageProperties.QUEUE_NAME)
            .deadLetterExchange(NotifyMessageProperties.DEAD_LETTER_EXCHANGE_NAME)
            .build();
    }

    @Bean
    Binding notifyBinding() {
        return BindingBuilder.bind(notifyQueue()).to(notifyExchange());
    }

    @Bean
    AmqpTemplate notifyRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        rabbitTemplate.setExchange(NotifyMessageProperties.EXCHANGE_NAME);
        return rabbitTemplate;
    }
}