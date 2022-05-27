package io.github.syakuis.rabbit.configuration

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@Configuration
class HealthCheckRabbitConfiguration(
    val connectionFactory: ConnectionFactory,
    val jsonMessageConverter: MessageConverter,
    val rabbitName: RabbitName) {
    @Bean
    fun serverInfoExchange(): DirectExchange {
        return ExchangeBuilder
            .directExchange(rabbitName.healthCheck.serverInfoExchange).build()
    }

    @Bean
    fun serverInfoQueue(): Queue {
        return QueueBuilder.durable(rabbitName.healthCheck.serverInfoQueue).build()
    }

    @Bean
    fun serverInfoBinding(): Binding {
        return BindingBuilder.bind(serverInfoQueue()).to(serverInfoExchange())
            .withQueueName()
    }

    @Bean
    fun basicHealthCheckRabbitListenerContainer(): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jsonMessageConverter)
        factory.setConcurrentConsumers(20)
        factory.setTaskExecutor(healthCheckTaskExecutor())
        return factory
    }

    @Bean("healthCheckTaskExecutor")
    fun healthCheckTaskExecutor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 20
        executor.setThreadNamePrefix("healthCheckTE-")
        executor.initialize()
        return executor
    }
}