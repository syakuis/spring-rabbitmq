package io.github.syakuis.rabbit.adaptor.`in`

import io.github.syakuis.rabbit.application.BasicHealthCheckService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
@RestController
@RequestMapping("/health-check/v1/health-checks")
class HealthCheckRestController(val healthCheckService: BasicHealthCheckService) {
    @PostMapping("/basic")
    fun basicRun(@RequestBody limit: Int) {
        healthCheckService.run(limit)
    }

    @PostMapping("/async")
    fun asyncRun(@RequestBody limit: Int) {
        healthCheckService.asyncRun(limit)
    }

    // todo socket 작업 (소켓 통신으로 구독 소비 지연)
}