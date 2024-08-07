package com.fourever.forever.data.repository

import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.BaseResponse
import com.fourever.forever.data.model.request.GetGeneratedQuestionsRequestDto
import com.fourever.forever.data.model.request.GetGeneratedSummaryRequestDto
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.GetFileListResponseDto
import com.fourever.forever.data.model.response.GetFileQuestionResponseDto
import com.fourever.forever.data.model.response.GetFileSummaryResponseDto
import com.fourever.forever.data.model.response.GetGeneratedQuestionsResponseDto
import com.fourever.forever.data.model.response.GetGeneratedSummaryResponseDto
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.data.model.response.PostFileResponseDto
import com.fourever.forever.data.model.response.PostFileSummaryResponseDto
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FileRepository {
    fun postFile(file: MultipartBody.Part): Flow<ResultWrapper<PostFileResponseDto>>

    fun getGeneratedSummary(getGeneratedSummaryRequestDto: GetGeneratedSummaryRequestDto): Flow<ResultWrapper<GetGeneratedSummaryResponseDto>>

    fun getGeneratedQuestions(getGeneratedQuestionsRequestDto: GetGeneratedQuestionsRequestDto): Flow<ResultWrapper<List<GetGeneratedQuestionsResponseDto.QuestionAndAnswer>>>

    fun getFileList(page: Int): Flow<ResultWrapper<BaseResponse<GetFileListResponseDto>>>

    fun postFileSummary(
        postFileSummaryRequestDto: PostFileSummaryRequestDto
    ): Flow<ResultWrapper<BaseResponse<PostFileSummaryResponseDto>>>

    fun postFileQuestion(
        documentId: Int,
        postFileQuestionRequestDto: PostFileQuestionRequestDto
    ): Flow<ResultWrapper<BaseResponse<Void>>>

    fun getFileSummary(documentId: Int): Flow<ResultWrapper<BaseResponse<GetFileSummaryResponseDto>>>

    fun getFileQuestionList(documentId: Int): Flow<ResultWrapper<BaseResponse<GetQuestionListDto>>>

    fun getFileQuestion(
        questionId: Int
    ): Flow<ResultWrapper<BaseResponse<GetFileQuestionResponseDto>>>
}