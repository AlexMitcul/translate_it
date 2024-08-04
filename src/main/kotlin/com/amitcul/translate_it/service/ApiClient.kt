package com.amitcul.translate_it.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.UnknownHostException
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Component
class ApiClient {
    @Value("\${api.url}")
    private lateinit var apiUrl: String

    @Value("\${api.key}")
    private lateinit var apiKey: String

    private val restTemplate = RestTemplate()
    private val objectMapper = ObjectMapper()

    private val numberOfThreads = 10
    private val executorService = Executors.newFixedThreadPool(numberOfThreads)

    fun getTranslation(text: String, sourceLang: String, targetLang: String): String {
        val words = text.split(" ")

        val tasks: List<Callable<String>> = words.map { word ->
            Callable { translateWord(word, sourceLang, targetLang) }
        }

        val futures: List<Future<String>> = executorService.invokeAll(tasks)

        return futures.joinToString(" ") { it.get() }

    }

    private fun translateWord(word: String, sourceLang: String, targetLang: String): String {
        val requestEntity = createRequestEntity(word, sourceLang, targetLang)

        val response: ResponseEntity<String> = try {
            restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String::class.java
            )
        } catch (e: UnknownHostException) {
            throw RuntimeException("Error occurred while calling the external API", e)
        }

        if (response.statusCode == HttpStatus.OK) {
            val responseBody = response.body ?: throw RuntimeException("Empty response from API")
            return extractTranslationFromResponse(responseBody)
        } else {
            TODO("Handle the HTTP errors")
        }
    }

    private fun createRequestEntity(word: String, sourceLang: String, targetLang: String): HttpEntity<String> {
        val headers = HttpHeaders().apply {
            set("Authorization", "DeepL-Auth-Key $apiKey")
            set("Content-Type", "application/json")
        }

        val requestBody = """
            {
                "text": ["$word"],
                "source_lang": "$sourceLang",
                "target_lang": "$targetLang"
            }
        """.trimIndent()

        return HttpEntity(requestBody, headers)
    }

    private fun extractTranslationFromResponse(responseBody: String): String {
        val responseJSON = objectMapper.readTree(responseBody)
        val translatedWord = responseJSON["translations"][0]
        return translatedWord["text"].asText()
    }
}