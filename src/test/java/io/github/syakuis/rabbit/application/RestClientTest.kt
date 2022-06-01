package io.github.syakuis.rabbit.application

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

class RestClientTest : BehaviorSpec() {
    private val log = LoggerFactory.getLogger(javaClass)
    init {
        Given("About the workflow of mono subscriptions." ) {

            val restClient = RestClient(500, 500)

            /**
             * doFirst: 역순으로 호출된다.
             */
            fun print(response: Mono<ResponseEntity<String>>) {
                response
                    .doFirst { log.debug("[테스트] doFirst 1") } // 2
                    .doFirst { log.debug("[테스트] doFirst 2") } // 1
                    .doAfterTerminate { log.debug("[테스트] doAfterTerminate") } // 8
                    .doOnSubscribe() { log.debug("[테스트] doOnSubscribe") } // 3
                    .doOnRequest { log.debug("[테스트] doOnRequest") } // 4
                    .onErrorResume {
                        log.debug("[테스트] onErrorResume")
                        Mono.just(ResponseEntity.ok().build())
                    }
                    .onErrorContinue { t, u ->
                        log.debug("[테스트] onErrorContinue") // 4 -> error -> 1
                        "a"
                    }.log()
                    .doOnTerminate { log.debug("[테스트] doOnTerminate") } // 5
                    .doOnNext { log.debug("[테스트] doOnNext") } // 6
                    .doOnCancel { log.debug("[테스트] doOnCancel") }
                    .doOnError { log.debug("[테스트] doOnError") }
                    .doFinally { log.debug("[테스트] doFinally") } // 9
                    .doOnSuccess { log.debug("[테스트] doOnSuccess") } // 7

                    .subscribe()

                log.debug("[테스트] -------------------------------- 끗")

                Thread.sleep(1000)
            }

            When("성공") {
                print(restClient.exchange("http://naver.com"))
            }

            When("실패") {
                print(restClient.exchange("http://192.168.150.1"))
            }

            When("실패") {
                print(restClient.exchange("http://222222"))
            }
        }
    }
}
