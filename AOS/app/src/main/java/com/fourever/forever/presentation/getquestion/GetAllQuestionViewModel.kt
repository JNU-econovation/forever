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

data class AllQuestionUiState(
    val questionState: UiState = UiState.Empty,
    val errorMessage: String = "",

    val questionIndex: Int = 1,
    val question: String = "",
    val answer: String = ""
)

@HiltViewModel
class GetAllQuestionViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    private val _allQuestionUiState = MutableStateFlow(AllQuestionUiState())
    val allQuestionUiState: StateFlow<AllQuestionUiState> = _allQuestionUiState.asStateFlow()


    fun getQuestion(questionId: Int) {
        viewModelScope.launch {
            fileRepository.getFileQuestion(questionId)
                .onStart { _allQuestionUiState.update { it.copy(questionState = UiState.Loading) }}
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            _allQuestionUiState.update {
                                it.copy(
                                    questionState = UiState.Success,
                                    question = result.data?.questionContent ?: "",
                                    answer = result.data?.answerContent ?: ""
                                )
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _allQuestionUiState.update {
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

    fun increaseQuestionIndex() {
        if(_allQuestionUiState.value.questionIndex < 5) {
            _allQuestionUiState.update {
                it.copy(
                    questionIndex = _allQuestionUiState.value.questionIndex + 1
                )
            }
        }
    }

    fun decreaseQuestionIndex() {
        if(_allQuestionUiState.value.questionIndex > 1) {
            _allQuestionUiState.update {
                it.copy(
                    questionIndex = _allQuestionUiState.value.questionIndex - 1
                )
            }
        }
    }
}