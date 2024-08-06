package com.amitcul.translate_it.controller

import com.amitcul.translate_it.service.TranslationService
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import kotlin.test.Test

@WebMvcTest(TranslationController::class)
class TranslationControllerTest {

    @MockBean
    private lateinit var translationService: TranslationService


//    @Test
//    fun `should return OK and save translation when translation is successful`() {
//        val text = "Hello"
//        val sourceLang = "en"
//        val targetLang = "es"
//        val translatedText = "Hola"
//
//        `when`(translationService.translate(text, sourceLang, targetLang))
//            .thenReturn(ResponseEntity.ok(translatedText))
//    }
//
//    @Test
//    fun `should return BAD REQUEST when translation fails`() {
//        val text = "Hello"
//        val sourceLang = "en"
//        val targetLang = "es"
//
//        `when`(translationService.translate(text, sourceLang, targetLang))
//            .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"))
//    }

}