package io.github.syakuis.producer.push.infrastucture.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
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
    private final MessageConverter jsonMessageConverter;

    public static final String EXCHANGE_NAME = "exchange.producer-server.push";

    @Bean
    public FanoutExchange pushExchange() {
        return ExchangeBuilder
            .fanoutExchange(EXCHANGE_NAME)
            .build();
    }

    @Bean
    public RabbitTemplate pushRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE_NAME);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}