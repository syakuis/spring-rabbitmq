package io.github.syakuis.rabbit.configuration;

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.syakuis.rabbit.configuration.support.SimpleObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BasicBeanConfiguration {
    @Bean
    fun objectMapper(): ObjectMapper {
        return SimpleObjectMapper.of(ObjectMapper())
    }
}
