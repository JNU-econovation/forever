package com.fourever.forever.data

import com.fourever.forever.data.model.BaseResponse
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.GetFileListResponseDto
import com.fourever.forever.data.model.response.GetFileQuestionResponseDto
import com.fourever.forever.data.model.response.GetFileSummaryResponseDto
import com.fourever.forever.data.model.response.GetQuestionListDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FileApiService {
    @GET("/api/documents")
    suspend fun getFileList(
        @Query(value = "page") page: Int,
    ): Result<BaseResponse<GetFileListResponseDto>>

    @POST("/api/documents/summary")
    suspend fun postFileSummary(
        @Body postFileSummaryRequestDto: PostFileSummaryRequestDto
    ): Result<BaseResponse<Void>>

    @POST("/api/documents/{documentId}/save")
    suspend fun postFileQuestion(
        @Path(value = "documentId") documentId: Int,
        @Body postFileQuestionRequestDto: PostFileQuestionRequestDto
    ): Result<BaseResponse<Void>>

    @GET("/api/documents/{documentId}/summary")
    suspend fun getFileSummary(
        @Path(value = "documentId") documentId: Int,
    ): Result<BaseResponse<GetFileSummaryResponseDto>>

    @GET("/api/documents/{documentId}/questions")
    suspend fun getFileQuestionList(
        @Path(value = "documentId") documentId: Int,
    ): Result<BaseResponse<GetQuestionListDto>>

    @GET("/api/documents/questions/{questionId}")
    suspend fun getFileQuestion(
        @Path(value = "questionId") questionId: Int,
    ): Result<BaseResponse<GetFileQuestionResponseDto>>
}