package com.fourever.forever.data.model.request

import kotlinx.serialization.SerialName

data class GetGeneratedSummaryRequestDto(
    @SerialName("upload_file_path")
    val uploadFilePath: String,
    @SerialName("file_name")
    val fileName: String
)
