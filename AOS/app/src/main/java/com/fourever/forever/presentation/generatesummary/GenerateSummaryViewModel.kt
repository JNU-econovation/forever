package com.fourever.forever.presentation.generatesummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.request.PostFileSummaryRequestDto
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

    fun postPdfFile(filePart: MultipartBody.Part) {
        viewModelScope.launch {
            fileRepository.postFile(filePart)
                .onStart {
                    _generateSummaryUiState.update { it.copy(generateSummaryState = UiState.Loading) }
                }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        _generateSummaryUiState.update {
                            it.copy(
                                generateSummaryState = UiState.Success
                            )
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

    fun getGeneratedSummary() {
        /* TODO: 요약 얻어오기 - 소켓 대체 가능 */
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

