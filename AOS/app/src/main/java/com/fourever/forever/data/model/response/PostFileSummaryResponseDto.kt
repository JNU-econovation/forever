package com.fourever.forever.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostFileSummaryResponseDto(
    @SerializedName("id")
    val id: Int
)
