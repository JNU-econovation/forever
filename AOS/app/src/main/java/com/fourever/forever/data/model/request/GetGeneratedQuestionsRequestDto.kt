package com.fourever.forever.data.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetGeneratedQuestionsRequestDto(
    @SerializedName("summarized_file_path")
    val filePath: String,
    @SerializedName("file_name")
    val fileName: String
)
