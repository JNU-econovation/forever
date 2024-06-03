package com.fourever.forever.data

import com.fourever.forever.data.model.response.GetGeneratedQuestionsResponseDto
import com.fourever.forever.data.model.response.GetGeneratedSummaryResponseDto
import com.fourever.forever.data.model.response.PostFileResponseDto
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AiApiService {
    @Multipart
    @POST("/api/upload")
    suspend fun postFile(
        @Part file: MultipartBody.Part
    ): Result<PostFileResponseDto>

    @GET("/api/upload/summary")
    suspend fun getGeneratedSummary(
        @Query("upload_file_path") uploadFilePath: String,
        @Query("file_name") fileName: String,
    ): Result<GetGeneratedSummaryResponseDto>

    @GET("/api/upload/questions")
    suspend fun getGeneratedQuestions(
        @Query("summarized_file_path") filePath: String,
        @Query("file_name") fileName: String
    ): Result<GetGeneratedQuestionsResponseDto>
}