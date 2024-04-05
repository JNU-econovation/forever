package com.fourever.forever.data.model.request

import kotlinx.serialization.SerialName

data class PostFileSummaryRequestDto(
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
)
