package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class GetGeneratedQuestionsResponseDto(
    @SerialName("questions")
    val questions: List<QuestionAndAnswer>
) {
    @Serializable
    data class QuestionAndAnswer(
        @SerialName("question")
        val question: String,
        @SerialName("answer")
        val answer: String
    )
}