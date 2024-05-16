package com.fourever.forever.presentation.generatequestion

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
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

data class GenerateQuestionUiState(
    val questionState: UiState = UiState.Empty,
    val errorMessage: String = "",

    val currentQuestion: String = "질문1",
    val currentAnswer: String = "답변1",

    val questionAndAnswerList: List<PostFileQuestionRequestDto> = listOf(
        PostFileQuestionRequestDto(
            "질문0", "답변0"
        ),
        PostFileQuestionRequestDto(
            "질문1", "답변1"
        ),
        PostFileQuestionRequestDto(
            "질문2", "답변2"
        ),
        PostFileQuestionRequestDto(
            "질문3", "답변3"
        ),
        PostFileQuestionRequestDto(
            "질문4", "답변4"
        ),
    ),
    val questionSaveStatus: MutableList<Boolean> = mutableStateListOf(
        false,
        false,
        false,
        false,
        false
    )
)

@HiltViewModel
class GenerateQuestionViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {
    private val _generateQuestionUiState = MutableStateFlow(GenerateQuestionUiState())
    val generateQuestionUiState: StateFlow<GenerateQuestionUiState> =
        _generateQuestionUiState.asStateFlow()

    fun toggleQuestionSaveStatus(questionIndex: Int) {
        val currentValue = generateQuestionUiState.value.questionSaveStatus[questionIndex]
        _generateQuestionUiState.value.questionSaveStatus[questionIndex] = !currentValue
    }

    fun postFileQuestion(documentId: Int) {
        for (i: Int in 0..4) {
            if (generateQuestionUiState.value.questionSaveStatus[i]) {
                postFileQuestionRequest(
                    documentId = documentId,
                    postFileQuestionRequestDto = generateQuestionUiState.value.questionAndAnswerList[i]
                )
            }
        }
    }

    private fun postFileQuestionRequest(
        documentId: Int,
        postFileQuestionRequestDto: PostFileQuestionRequestDto
    ) {
        viewModelScope.launch {
            fileRepository.postFileQuestion(
                documentId = documentId,
                postFileQuestionRequestDto = postFileQuestionRequestDto
            )
                .onStart { _generateQuestionUiState.update { it.copy(questionState = UiState.Loading) } }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            if (result.status) {
                                _generateQuestionUiState.update {
                                    it.copy(
                                        questionState = UiState.Success
                                    )
                                }
                            } else {
                                _generateQuestionUiState.update {
                                    it.copy(
                                        questionState = UiState.Failure
                                    )
                                }
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _generateQuestionUiState.update {
                            it.copy(
                                questionState = UiState.Failure
                            )
                        }
                    }
                }
        }
    }
}