package com.fourever.forever.presentation.getquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
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

data class QuestionUiState(
    val questionState: UiState = UiState.Empty,
    val errorMessage: String = "",
    val question: String = "",
    val answer: String = ""
)

@HiltViewModel
class GetQuestionViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    private val _questionUiState = MutableStateFlow(QuestionUiState())
    val questionUiState: StateFlow<QuestionUiState> = _questionUiState.asStateFlow()


    fun getQuestion(documentId: Int, questionId: Int) {
        viewModelScope.launch {
            fileRepository.getFileQuestion(documentId, questionId)
                .onStart { _questionUiState.update { it.copy(questionState = UiState.Loading) }}
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            _questionUiState.update {
                                it.copy(
                                    questionState = UiState.Success,
                                    question = result.data?.content ?: "",
                                    answer = result.data?.answer ?: ""
                                )
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _questionUiState.update {
                            it.copy(
                                questionState = UiState.Failure,
                                errorMessage = result.errorMessage,
                                question = "",
                                answer = ""
                            )
                        }
                    }
                }
        }
    }
}