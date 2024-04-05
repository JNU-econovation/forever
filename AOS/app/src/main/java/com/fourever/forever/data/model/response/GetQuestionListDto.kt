package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class GetQuestionListDto(
    @SerialName("questions")
    val questions: List<Questions>
) {
    @Serializable
    data class Questions(
        @SerialName("questionId")
        val questionId: Int,
        @SerialName("title")
        val content: String
    )
}
