package com.amitcul.translate_it.dao

import com.amitcul.translate_it.model.Translation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class TranslationDAO(
    private val jdbcTemplate: JdbcTemplate
) {

    fun save(translation: Translation): Translation {
        val sql = """
            INSERT INTO translation_requests (ip_address, source_text, translated_text, target_lang, source_lang, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()
        jdbcTemplate.update(sql,
            translation.ipAddress,
            translation.sourceText,
            translation.translatedText,
            translation.targetLang,
            translation.sourceLang,
            translation.createdAt
        )
        return translation
    }
}