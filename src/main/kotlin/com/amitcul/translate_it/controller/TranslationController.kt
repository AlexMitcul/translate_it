package com.amitcul.translate_it.controller

import com.amitcul.translate_it.model.Translation
import com.amitcul.translate_it.service.TranslationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/translate")
class TranslationController(
    @Autowired private val translationService: TranslationService
) {

    @PostMapping
    fun translate(
        @RequestParam text: String,
        @RequestParam sourceLang: String,
        @RequestParam targetLang: String,
        @RequestHeader(value = "X-Forwarded-For", required = false) ipAddress: String?
    ): Translation {
        val clientIpAddress = ipAddress?.takeIf { it.isNotEmpty() } ?: "IP not provided"
        return translationService.translateAndSave(text, sourceLang, targetLang, clientIpAddress)
    }

}