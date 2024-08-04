package com.amitcul.translate_it.service

import com.amitcul.translate_it.dao.TranslationDAO
import com.amitcul.translate_it.model.Translation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class TranslationService(
    @Autowired private val translationDao: TranslationDAO,
    @Autowired private val apiClient: ApiClient
) {

    fun translateAndSave(text: String, sourceLang: String, targetLang: String, ipAddress: String): Translation {
        val translatedText = apiClient.getTranslation(text, sourceLang, targetLang)

        val translation = Translation(
            ipAddress = ipAddress,
            sourceText = text,
            translatedText = translatedText,
            sourceLang = sourceLang,
            targetLang = targetLang,
            createdAt = LocalDateTime.now()
        )

        return translationDao.save(translation)
    }

}