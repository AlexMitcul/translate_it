package com.amitcul.translate_it.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicReference

@Service
class TranslationService(private val restTemplate: RestTemplate,
                         private val objectMapper: ObjectMapper
) {

    @Value("\${api.url}")
    private lateinit var apiUrl: String

    @Value("\${api.key}")
    private lateinit var apiKey: String

    private val executorService: ExecutorService = Executors.newFixedThreadPool(10)

    fun translate(inputText: String, sourceLang: String, targetLang: String): ResponseEntity<String> {
        val words = inputText.split(" ")
        val firstError = AtomicReference<String?>(null)

        val futures = words.map { word ->
            CompletableFuture.supplyAsync({
                if (firstError.get() != null) return@supplyAsync null

                try {
                    val headers = HttpHeaders().apply {
                        set("Authorization", "DeepL-Auth-Key $apiKey")
                        set("User-Agent", "YourApp/1.2.3")
                        contentType = MediaType.APPLICATION_JSON
                    }

                    val requestBody = mapOf(
                        "text" to listOf(word),
                        "target_lang" to targetLang,
                        "source_lang" to sourceLang
                    )

                    val entity = HttpEntity(objectMapper.writeValueAsString(requestBody), headers)
                    val response: ResponseEntity<String> = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.POST,
                        entity,
                        String::class.java
                    )

                    if (response.statusCode == HttpStatus.OK) {
                        val responseBody = response.body ?: "Ошибка получения перевода"
                        val resultMap = objectMapper.readValue(responseBody, Map::class.java) as Map<String, Any>
                        val translations = resultMap["translations"] as List<Map<String, Any>>
                        translations.firstOrNull()?.get("text") as? String ?: "Ошибка перевода"
                    } else {
                        "Ошибка перевода: ${response.statusCode}"
                    }
                } catch (e: HttpClientErrorException) {
                    val errorMessage = extractErrorMessage(e.responseBodyAsString)
                    firstError.set(errorMessage)
                    return@supplyAsync errorMessage
                }
            }, executorService).exceptionally { ex ->
                if (firstError.get() == null) {
                    val errorMessage = "Неизвестная ошибка: ${ex.message}"
                    firstError.set(errorMessage)
                    errorMessage
                } else {
                    null
                }
            }
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        val errorMessage = firstError.get()
        return if (errorMessage != null) {
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        } else {
            val translatedWords = futures.map { it.getNow("") }.joinToString(" ").trim()
            ResponseEntity(translatedWords, HttpStatus.OK)
        }
    }

    private fun extractErrorMessage(responseBody: String): String {
        return try {
            val errorMap = objectMapper.readValue(responseBody, Map::class.java) as Map<String, Any>
            errorMap["message"] as? String ?: "Неизвестная ошибка"
        } catch (e: Exception) {
            "Неизвестная ошибка"
        }
    }

}