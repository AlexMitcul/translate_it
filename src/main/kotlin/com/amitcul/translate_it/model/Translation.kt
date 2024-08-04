package com.amitcul.translate_it.model

import java.time.LocalDateTime

data class Translation(
    var id: Long? = null,
    var ipAddress: String,
    var sourceText: String,
    var translatedText: String,
    var sourceLang: String,
    var targetLang: String,
    var createdAt: LocalDateTime
)