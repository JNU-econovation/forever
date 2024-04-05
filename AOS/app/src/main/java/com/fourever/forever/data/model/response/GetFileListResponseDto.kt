package com.fourever.forever.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class GetFileListResponseDto(
    @SerialName("documents")
    val programs: List<Document>
) {
    @Serializable
    data class Document(
        @SerialName("documentId")
        val documentId: Int,
        @SerialName("title")
        val title: String
    )
}
