package com.fourever.forever.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostFileResponseDto(
    @SerializedName("upload_file_path")
    val uploadFilePath: String,
    @SerializedName("file_name")
    val fileName: String
)
