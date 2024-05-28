package com.fourever.forever.data.repository

import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.datasource.FileDataSource
import com.fourever.forever.data.model.BaseResponse
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.GetFileListResponseDto
import com.fourever.forever.data.model.response.GetFileQuestionResponseDto
import com.fourever.forever.data.model.response.GetFileSummaryResponseDto
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.data.model.response.PostFileSummaryResponseDto
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource
): FileRepository {
    override fun postFile(file: MultipartBody.Part): Flow<ResultWrapper<Unit>> =
        fileDataSource.postFile(file)

    override fun getFileList(page: Int): Flow<ResultWrapper<BaseResponse<GetFileListResponseDto>>> =
        fileDataSource.getFileList(page)

    override fun postFileSummary(
        postFileSummaryRequestDto: PostFileSummaryRequestDto
    ): Flow<ResultWrapper<BaseResponse<PostFileSummaryResponseDto>>> =
        fileDataSource.postFileSummary(postFileSummaryRequestDto)

    override fun postFileQuestion(
        documentId: Int,
        postFileQuestionRequestDto: PostFileQuestionRequestDto
    ): Flow<ResultWrapper<BaseResponse<Void>>> =
        fileDataSource.postFileQuestion(documentId, postFileQuestionRequestDto)

    override fun getFileSummary(documentId: Int): Flow<ResultWrapper<BaseResponse<GetFileSummaryResponseDto>>> =
        fileDataSource.getFileSummary(documentId)

    override fun getFileQuestionList(documentId: Int): Flow<ResultWrapper<BaseResponse<GetQuestionListDto>>> =
        fileDataSource.getFileQuestionList(documentId)

    override fun getFileQuestion(
        questionId: Int
    ): Flow<ResultWrapper<BaseResponse<GetFileQuestionResponseDto>>> =
        fileDataSource.getFileQuestion(questionId)
}