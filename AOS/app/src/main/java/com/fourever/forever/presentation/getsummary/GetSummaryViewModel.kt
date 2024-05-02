package com.fourever.forever.presentation.getsummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.data.repository.FileRepository
import com.fourever.forever.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SummaryUiState(
    val summaryState: UiState = UiState.Empty,
    val errorMessage: String = "",
    val title: String = "",
    val summary: String = ""
)

data class QuestionListUiState(
    val questionListState: UiState = UiState.Empty,
    val errorMessage: String = "",
    val questionList: List<GetQuestionListDto.Questions> = listOf()
)


@HiltViewModel
class GetSummaryViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    private val _summaryUiState = MutableStateFlow(SummaryUiState())
    val summaryUiState: StateFlow<SummaryUiState> = _summaryUiState.asStateFlow()

    private val _questionListUiState = MutableStateFlow(QuestionListUiState())
    val questionListUiState: StateFlow<QuestionListUiState> = _questionListUiState.asStateFlow()

    fun getSummary(documentId: Int) {
        viewModelScope.launch {
            fileRepository.getFileSummary(documentId)
                .onStart { _summaryUiState.update { it.copy(summaryState = UiState.Loading) } }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            _summaryUiState.update {
                                it.copy(
                                    summaryState = UiState.Success,
                                    title = result.data?.title ?: "",
                                    summary = result.data?.summary ?: ""
                                )
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _summaryUiState.update {
                            it.copy(
                                summaryState = UiState.Failure,
                                errorMessage = result.errorMessage,
                                title = "",
                                summary = ""
                            )
                        }
                    }
                }
        }
    }

    fun getQuestionList(documentId: Int) {
        viewModelScope.launch {
            fileRepository.getFileQuestionList(documentId)
                .onStart { _questionListUiState.update { it.copy(questionListState = UiState.Loading) } }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            _questionListUiState.update {
                                it.copy(
                                    questionListState = UiState.Success,
                                    questionList = result.data?.questions ?: listOf()
                                )
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _questionListUiState.update {
                            it.copy(
                                questionListState = UiState.Failure,
                                errorMessage = result.errorMessage,
                                questionList =listOf()
                            )
                        }
                    }
                }
        }
    }
}