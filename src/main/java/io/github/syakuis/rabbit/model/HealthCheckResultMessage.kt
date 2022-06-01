package io.github.syakuis.rabbit.model

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-28
 */
data class HealthCheckResultMessage(val error: Boolean, val message: String, val serverInfoDto: ServerInfoDto)