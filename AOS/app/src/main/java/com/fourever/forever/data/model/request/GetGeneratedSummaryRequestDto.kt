package com.fourever.forever.data.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetGeneratedSummaryRequestDto(
    @SerializedName("upload_file_path")
    val uploadFilePath: String,
    @SerializedName("file_name")
    val fileName: String
)
