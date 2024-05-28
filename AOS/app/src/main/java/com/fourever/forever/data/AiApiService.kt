package com.fourever.forever.data


import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AiApiService {
    @Multipart
    @POST("/api/upload")
    suspend fun postFile(
        @Part file: MultipartBody.Part
    ): Result<Unit>
}