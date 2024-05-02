package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName

data class GetFileQuestionResponseDto(
    @SerialName("questionContent")
    val questionContent: String,
    @SerialName("answerContent")
    val answerContent: String,
)
