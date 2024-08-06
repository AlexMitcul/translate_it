package com.amitcul.translate_it.controller

import com.amitcul.translate_it.dao.TranslationDAO
import com.amitcul.translate_it.model.Translation
import com.amitcul.translate_it.service.TranslationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/translate")
class TranslationController(
    private val translationService: TranslationService,
    private val translationDAO: TranslationDAO
) {

    @PostMapping
    fun translate(
        @RequestParam text: String,
        @RequestParam sourceLang: String,
        @RequestParam targetLang: String,
        @RequestHeader(value = "X-Forwarded-For", required = false) ipAddress: String?
    ): ResponseEntity<String> {
        val response: ResponseEntity<String> =
            translationService.translate(text, sourceLang, targetLang)

        if (response.statusCode == HttpStatus.OK) {
            translationDAO.save(
                Translation(
                    ipAddress = ipAddress ?: "IP address not provided",
                    sourceText = text,
                    translatedText = response.body.toString(),
                    targetLang = targetLang,
                    sourceLang = sourceLang,
                    createdAt = LocalDateTime.now()
                )
            )
        }

        return ResponseEntity("${response.statusCode}: ${response.body}", response.statusCode)
    }

}