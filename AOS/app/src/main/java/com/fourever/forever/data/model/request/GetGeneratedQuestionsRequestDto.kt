package com.fourever.forever.data.model.request

import kotlinx.serialization.SerialName

data class GetGeneratedQuestionsRequestDto(
    @SerialName("summarized_file_path")
    val filePath: String,
    @SerialName("file_name")
    val fileName: String
)
