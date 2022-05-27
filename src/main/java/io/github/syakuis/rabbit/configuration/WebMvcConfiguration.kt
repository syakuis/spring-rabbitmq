package io.github.syakuis.rabbit.configuration;

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
import java.nio.charset.StandardCharsets

@EnableAsync
@Configuration(proxyBeanMethods = false)
class WebMvcConfiguration(private val objectMapper: ObjectMapper) : DelegatingWebMvcConfiguration() {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val stringConverter = StringHttpMessageConverter(StandardCharsets.UTF_8)
        converters.add(stringConverter)
        converters.add(MappingJackson2HttpMessageConverter(objectMapper))
        super.addDefaultHttpMessageConverters(converters)
    }
}
