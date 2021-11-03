package io.github.syakuis.producer.push.infrastucture.amqp;

import io.github.syakuis.producer.push.model.NotifyMessageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-05-21
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeadLetterNotifyRabbitConfiguration {

    @Bean
    FanoutExchange deadLetterNotifyExchange() {
        return ExchangeBuilder
            .fanoutExchange(NotifyMessageProperties.DEAD_LETTER_EXCHANGE_NAME)
            .build();
    }

    @Bean
    Queue deadLetterNotifyQueue() {
        return QueueBuilder
            .durable(NotifyMessageProperties.DEAD_LETTER_QUEUE_NAME)
            .build();
    }

    @Bean
    Binding deadLetterNotifyBinding() {
        return BindingBuilder.bind(deadLetterNotifyQueue()).to(deadLetterNotifyExchange());
    }
}