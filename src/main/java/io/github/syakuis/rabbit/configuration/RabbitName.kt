package io.github.syakuis.rabbit.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@ConstructorBinding
@ConfigurationProperties("rabbitmq.name")
data class RabbitName(
    val healthCheck: HealthCheck
) {
    data class HealthCheck(val serverInfoExchange: String, val serverInfoQueue: String) {
    }
}