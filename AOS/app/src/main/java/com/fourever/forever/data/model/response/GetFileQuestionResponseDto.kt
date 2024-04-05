package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName

data class GetFileQuestionResponseDto(
    @SerialName("content")
    val content: String,
    @SerialName("answer")
    val answer: String,
)
