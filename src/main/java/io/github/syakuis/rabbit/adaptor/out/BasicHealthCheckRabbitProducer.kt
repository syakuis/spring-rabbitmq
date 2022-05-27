package io.github.syakuis.rabbit.adaptor.out

import io.github.syakuis.rabbit.configuration.RabbitName
import io.github.syakuis.rabbit.model.ServerInfoMessage
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.AsyncAmqpTemplate
import org.springframework.stereotype.Component

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@Component
class BasicHealthCheckRabbitProducer(val defaultRabbitTemplate: AmqpTemplate, val defaultAsyncRabbitTemplate: AsyncAmqpTemplate, val rabbitName: RabbitName) {

    fun asyncServerInfo(serverInfoMessage: ServerInfoMessage) {
        defaultAsyncRabbitTemplate.convertSendAndReceive<ServerInfoMessage>(
            rabbitName.healthCheck.serverInfoExchange,
            rabbitName.healthCheck.serverInfoQueue,
            serverInfoMessage
        )
    }

    fun serverInfo(serverInfoMessage: ServerInfoMessage) {
        defaultRabbitTemplate.convertAndSend(
            rabbitName.healthCheck.serverInfoExchange,
            rabbitName.healthCheck.serverInfoQueue,
            serverInfoMessage
        )
    }
}