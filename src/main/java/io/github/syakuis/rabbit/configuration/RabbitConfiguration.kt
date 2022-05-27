package io.github.syakuis.rabbit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.AsyncAmqpTemplate
import org.springframework.amqp.rabbit.AsyncRabbitTemplate
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@Configuration
class RabbitConfiguration(
    val objectMapper: ObjectMapper,
    val connectionFactory: ConnectionFactory
) {
    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun defaultRabbitListenerContainer(): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jsonMessageConverter())
        return factory
    }

    @Bean
    fun defaultRabbitTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun defaultAsyncRabbitTemplate(connectionFactory: ConnectionFactory): AsyncAmqpTemplate? {
        return AsyncRabbitTemplate(defaultRabbitTemplate(connectionFactory) as RabbitTemplate)
    }
}