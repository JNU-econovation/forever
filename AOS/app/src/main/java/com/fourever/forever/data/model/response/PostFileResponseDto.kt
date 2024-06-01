package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName

data class PostFileResponseDto(
    @SerialName("upload_file_path")
    val uploadFilePath: String,
    @SerialName("file_name")
    val fileName: String
)
