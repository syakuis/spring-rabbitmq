package io.github.syakuis.consumer.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author Seok Kyun. Choi.
 * @since 2021-11-03
 */
@RequiredArgsConstructor
@Configuration
public class NotifyRabbitConfiguration {
    @Bean
    SimpleRabbitListenerContainerFactory listenerContainer(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setAdviceChain(RetryInterceptorBuilder.stateless()

            .maxAttempts(3)
            .backOffOptions(Duration.ofSeconds(3L).toMillis(), 2, Duration.ofSeconds(10L).toMillis())
            .recoverer(null)
            .build());

        return container;
    }

    @Bean
    SimpleRabbitListenerContainerFactory deadListenerContainer(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
