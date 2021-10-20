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
public class RetryPushRabbitConfiguration {
    private final MessageConverter messageConverter;
    public static final String DEAD_LETTER_EXCHANGE_NAME = "dead-letter-exchange.retry-push";
    public static final String DEAD_LETTER_QUEUE_NAME = "dead-letter-queue.retry-push";
    public static final String EXCHANGE_NAME = "exchange.retry-push";
    public static final String QUEUE_NAME = "queue.retry-push";

    @Bean
    FanoutExchange retryPushDeadLetterExchange() {
        return ExchangeBuilder
            .fanoutExchange(DEAD_LETTER_EXCHANGE_NAME)
            .build();
    }

    @Bean
    Queue deadLetterRetryPushQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean
    Binding deadLetterRetryPushBinding() {
        return BindingBuilder.bind(deadLetterRetryPushQueue()).to(retryPushDeadLetterExchange());
    }

    @Bean
    FanoutExchange retryPushExchange() {
        return ExchangeBuilder
            .fanoutExchange(EXCHANGE_NAME)
            .build();
    }

    @Bean
    Queue retryPushQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
            .deadLetterExchange(DEAD_LETTER_EXCHANGE_NAME)
            .deadLetterRoutingKey("dead-letter")
            .build();
    }

    @Bean
    Binding retryPushBinding() {
        return BindingBuilder.bind(retryPushQueue()).to(retryPushExchange());
    }

    @Bean
    public AmqpTemplate retryPushRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        return rabbitTemplate;
    }
}