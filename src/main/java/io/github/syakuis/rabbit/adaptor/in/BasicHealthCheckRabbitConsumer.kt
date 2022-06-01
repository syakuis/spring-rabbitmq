package io.github.syakuis.rabbit.adaptor.`in`

import io.github.syakuis.rabbit.adaptor.out.HealthCheckWebSocketSender
import io.github.syakuis.rabbit.application.RestClient
import io.github.syakuis.rabbit.model.ServerInfoMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.function.Consumer

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@Component
class BasicHealthCheckRabbitConsumer(val healthCheckWebSocketSender: HealthCheckWebSocketSender) {
    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(
        queues = ["#{serverInfoQueue.getName()}"],
        containerFactory = "basicHealthCheckRabbitListenerContainer"
    )
    fun serverInfo(@Payload serverInfoMessage: ServerInfoMessage, @Header("quota") quota: Int) {
        log.info("[로그] 헬스 체크를 위한 호스트 정보를 구독했습니다.")
        val response: Mono<ResponseEntity<String>> = RestClient(500, 500).exchange(serverInfoMessage.host)

        response.subscribe(Consumer {
//            log.info("[로그] 요청 성공: {}", it.body)
        }) {
//            log.error("[로그] 요청 실패: {}", it.message)
        }
    }
}