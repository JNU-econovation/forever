package com.fourever.forever.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String?,
    @SerialName("data")
    val data: T?
)