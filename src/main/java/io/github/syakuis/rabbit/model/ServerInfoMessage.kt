package io.github.syakuis.rabbit.model

/**
 * @author Seok Kyun. Choi.
 * @since 2022-05-27
 */
data class ServerInfoMessage(val host: String, val name: String, val text: String) {
}