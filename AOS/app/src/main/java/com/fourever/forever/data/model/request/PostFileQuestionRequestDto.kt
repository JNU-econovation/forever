package com.fourever.forever.data.model.request

import kotlinx.serialization.SerialName

data class PostFileQuestionRequestDto(
    @SerialName("title")
    val content: String,
    @SerialName("summary")
    val answer: String,
)
