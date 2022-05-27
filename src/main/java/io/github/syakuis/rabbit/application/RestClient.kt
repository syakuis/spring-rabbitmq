package io.github.syakuis.rabbit.application

import io.netty.channel.ChannelOption
import org.springframework.http.ResponseEntity
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
class RestClient(private val connectionTimeout: Int, private val responseTimeout: Int) {
    private val webClient: WebClient

    init {
        val connector = ReactorClientHttpConnector(
            HttpClient.create()
                .responseTimeout(Duration.ofMillis(responseTimeout.toLong()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
        )

        val builder = WebClient.builder()
            .clientConnector(connector)

        this.webClient = builder.build()
    }

    fun exchange(host: String): Mono<ResponseEntity<String>> {
        return this.webClient.get().uri(host)
            .retrieve()
            .toEntity()
    }
}