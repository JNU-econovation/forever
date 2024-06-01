package com.fourever.forever.data


import com.fourever.forever.data.model.request.GetGeneratedQuestionsRequestDto
import com.fourever.forever.data.model.request.GetGeneratedSummaryRequestDto
import com.fourever.forever.data.model.response.GetGeneratedQuestionsResponseDto
import com.fourever.forever.data.model.response.GetGeneratedSummaryResponseDto
import com.fourever.forever.data.model.response.PostFileResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AiApiService {
    @Multipart
    @POST("/api/upload")
    suspend fun postFile(
        @Part file: MultipartBody.Part
    ): Result<PostFileResponseDto>

    @GET("/api/upload/summary")
    suspend fun getGeneratedSummary(
        @Body getGeneratedSummaryRequestDto: GetGeneratedSummaryRequestDto
    ): Result<GetGeneratedSummaryResponseDto>

    @GET("/api/upload/questions")
    suspend fun getGeneratedQuestions(
        @Body getGeneratedQuestionsRequestDto: GetGeneratedQuestionsRequestDto
    ): Result<GetGeneratedQuestionsResponseDto>
}