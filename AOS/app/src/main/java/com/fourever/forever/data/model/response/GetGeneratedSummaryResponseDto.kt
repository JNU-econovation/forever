package com.fourever.forever.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetGeneratedSummaryResponseDto(
    @SerializedName("summarized_file_path")
    val filePath: String,
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("summarized_input")
    val summary: String
)
