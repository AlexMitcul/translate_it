package com.amitcul.translate_it.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class TranslationService(
    private val restTemplate: RestTemplate
) {

    @Value("\${api.key}")
    private lateinit var apiKey: String

    fun translate(text: String, sourceLanguage: String, targetLanguage: String): String {
        val url = "https://api-free.deepl.com/v2/translate"

        val headers = HttpHeaders().apply {
            set("Authorization", "DeepL-Auth-Key $apiKey")
            set("Content-Type", "application/json")
        }

        val requestBody = """
            {
                "text": ["$text"],
                "source_lang": "$sourceLanguage",
                "target_lang": "$targetLanguage"
            }
        """.trimIndent()

        val requestEntity = HttpEntity(requestBody, headers)

        val response: ResponseEntity<String> = restTemplate.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            String::class.java
        )

        if (response.statusCode == HttpStatus.OK) {
            return response.body ?: throw RuntimeException("No response body")
        } else {
            throw RuntimeException("Failed to translate text.")
        }
    }




}