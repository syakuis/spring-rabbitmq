package io.github.syakuis.producer.push.infrastucture.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
    private final MessageConverter jsonMessageConverter;

    public static final String EXCHANGE_NAME = "exchange.producer-server.retry-push";

    @Bean
    public FanoutExchange retryPushExchange() {
        return ExchangeBuilder
            .fanoutExchange(EXCHANGE_NAME)
            .build();
    }

    @Bean
    public RabbitTemplate retryPushRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}