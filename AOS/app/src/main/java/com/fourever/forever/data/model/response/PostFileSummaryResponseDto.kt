package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName

data class PostFileSummaryResponseDto(
    @SerialName("documentId")
    val documentId: Int
)
