package com.fourever.forever.data.model.request

import kotlinx.serialization.SerialName

data class PostFileQuestionRequestDto(
    @SerialName("questionContent")
    val questionContent: String,
    @SerialName("answerContent")
    val answerContent: String,
)
