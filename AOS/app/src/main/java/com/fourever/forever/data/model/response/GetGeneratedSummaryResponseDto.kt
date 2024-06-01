package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName

data class GetGeneratedSummaryResponseDto(
    @SerialName("summarized_file_path")
    val filePath: String,
    @SerialName("file_name")
    val fileName: String,
    @SerialName("summarized_input")
    val summary: String
)
