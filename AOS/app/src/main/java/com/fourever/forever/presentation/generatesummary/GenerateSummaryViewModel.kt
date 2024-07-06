package com.fourever.forever.presentation.generatesummary

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.request.GetGeneratedQuestionsRequestDto
import com.fourever.forever.data.model.request.GetGeneratedSummaryRequestDto
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
import com.fourever.forever.data.model.response.PostFileResponseDto
import com.fourever.forever.data.repository.FileRepository
import com.fourever.forever.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

data class GenerateSummaryUiState(
    val postPdfState: UiState = UiState.Empty,
    val generateSummaryState: UiState = UiState.Empty,
    val summary: String = "",
    val documentId: Int = 0
)

@HiltViewModel
class GenerateSummaryViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    private val _generateSummaryUiState = MutableStateFlow(GenerateSummaryUiState())
    val generateSummaryUiState = _generateSummaryUiState.asStateFlow()

    lateinit var getGeneratedQuestionRequestDto: GetGeneratedQuestionsRequestDto

    fun postPdfFile(filePart: MultipartBody.Part) {
        viewModelScope.launch {
            fileRepository.postFile(filePart)
                .onStart {
                    _generateSummaryUiState.update { it.copy(postPdfState = UiState.Loading) }
                }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        _generateSummaryUiState.update {
                            it.copy(
                                postPdfState = UiState.Success
                            )
                        }

                        val postFileResponseDto = result.data
                        Handler(Looper.getMainLooper()).postDelayed({
                            getGeneratedSummary(dtoAdapter(postFileResponseDto))
                        }, 1000)
                    } else if (result is ResultWrapper.Error) {
                        _generateSummaryUiState.update {
                            it.copy(
                                postPdfState = UiState.Failure
                            )
                        }
                    }
                }
        }
    }

    private fun getGeneratedSummary(getGeneratedSummaryRequestDto: GetGeneratedSummaryRequestDto) {
        viewModelScope.launch {
            fileRepository.getGeneratedSummary(getGeneratedSummaryRequestDto)
                .onStart {
                    _generateSummaryUiState.update { it.copy(generateSummaryState = UiState.Loading) }
                }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        _generateSummaryUiState.update {
                            it.copy(
                                generateSummaryState = UiState.Success,
                                summary = result.data.summary
                            )
                        }
                        getGeneratedQuestionRequestDto = GetGeneratedQuestionsRequestDto(
                            fileName = result.data.fileName,
                            filePath = result.data.filePath
                        )
                    } else if (result is ResultWrapper.Error) {
                        _generateSummaryUiState.update {
                            it.copy(
                                generateSummaryState = UiState.Failure
                            )
                        }
                    }
                }
        }
    }

    fun postFileSummary(title: String) {
        viewModelScope.launch {
            fileRepository.postFileSummary(
                PostFileSummaryRequestDto(
                    title = title,
                    summary = generateSummaryUiState.value.summary
                )
            )
                .onStart { _generateSummaryUiState.update { it.copy(generateSummaryState = UiState.Loading) } }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            if (result.status) {
                                _generateSummaryUiState.update {
                                    it.copy(
                                        generateSummaryState = UiState.Success,
                                        documentId = result.data?.id ?: 0
                                    )
                                }
                            } else {
                                _generateSummaryUiState.update {
                                    it.copy(
                                        generateSummaryState = UiState.Failure
                                    )
                                }
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _generateSummaryUiState.update {
                            it.copy(
                                generateSummaryState = UiState.Failure
                            )
                        }
                    }
                }
        }
    }
}

private fun dtoAdapter(postFileResponseDto: PostFileResponseDto): GetGeneratedSummaryRequestDto {
    return GetGeneratedSummaryRequestDto(
        uploadFilePath = postFileResponseDto.uploadFilePath,
        fileName = postFileResponseDto.fileName
    )
}