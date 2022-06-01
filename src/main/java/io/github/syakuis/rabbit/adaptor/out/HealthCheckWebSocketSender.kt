package io.github.syakuis.rabbit.adaptor.out

import io.github.syakuis.rabbit.model.HealthCheckResultMessage
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-28
 */
@Component
class HealthCheckWebSocketSender(val simpMessagingTemplate: SimpMessagingTemplate) {

    fun result(healthCheckResultMessage: HealthCheckResultMessage) {
        simpMessagingTemplate.convertAndSend("/topic/health-check-result", healthCheckResultMessage)
    }
}