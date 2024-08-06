package com.amitcul.translate_it.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class AppConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()

}