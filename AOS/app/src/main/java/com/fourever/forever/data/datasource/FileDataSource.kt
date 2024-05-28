package com.fourever.forever.data.datasource

import com.fourever.forever.data.AiApiService
import com.fourever.forever.data.FileApiService
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.BaseResponse
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.GetFileListResponseDto
import com.fourever.forever.data.model.response.GetFileQuestionResponseDto
import com.fourever.forever.data.model.response.GetFileSummaryResponseDto
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.data.model.response.PostFileSummaryResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class FileDataSource @Inject constructor(
    private val fileApiService: FileApiService,
    private val aiApiService: AiApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun postFile(file: MultipartBody.Part): Flow<ResultWrapper<Unit>> =
        flow {
            aiApiService.postFile(file)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)

    fun getFileList(page: Int): Flow<ResultWrapper<BaseResponse<GetFileListResponseDto>>> =
        flow {
            fileApiService.getFileList(page)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)

    fun postFileSummary(postFileSummaryRequestDto: PostFileSummaryRequestDto): Flow<ResultWrapper<BaseResponse<PostFileSummaryResponseDto>>> =
        flow {
            fileApiService.postFileSummary(postFileSummaryRequestDto)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)

    fun postFileQuestion(
        documentId: Int,
        postFileQuestionRequestDto: PostFileQuestionRequestDto
    ): Flow<ResultWrapper<BaseResponse<Void>>> =
        flow {
            fileApiService.postFileQuestion(documentId, postFileQuestionRequestDto)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)

    fun getFileSummary(documentId: Int): Flow<ResultWrapper<BaseResponse<GetFileSummaryResponseDto>>> =
        flow {
            fileApiService.getFileSummary(documentId)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)

    fun getFileQuestionList(documentId: Int): Flow<ResultWrapper<BaseResponse<GetQuestionListDto>>> =
        flow {
            fileApiService.getFileQuestionList(documentId)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)

    fun getFileQuestion(questionId: Int): Flow<ResultWrapper<BaseResponse<GetFileQuestionResponseDto>>> =
        flow {
            fileApiService.getFileQuestion(questionId)
                .onSuccess { emit(ResultWrapper.Success(it)) }
                .onFailure { emit(ResultWrapper.Error(it.message!!)) }
        }.flowOn(ioDispatcher)
}