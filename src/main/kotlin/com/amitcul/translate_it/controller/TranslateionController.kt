package com.amitcul.translate_it.controller

import com.amitcul.translate_it.service.TranslationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class TranslateionController(
    private val translationService: TranslationService
) {

    @GetMapping("/translate")
    fun translate(
        @RequestParam text: String,
        @RequestParam sourceLanguage: String,
        @RequestParam targetLanguage: String
    ): String {
        return translationService.translate(text, sourceLanguage, targetLanguage)
    }

}