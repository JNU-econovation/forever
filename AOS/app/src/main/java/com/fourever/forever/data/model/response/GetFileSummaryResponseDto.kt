package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName

data class GetFileSummaryResponseDto(
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
)
