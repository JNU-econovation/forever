package com.fourever.forever.presentation.generatequestion

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.request.GetGeneratedQuestionsRequestDto
import com.fourever.forever.data.model.request.PostFileQuestionRequestDto
import com.fourever.forever.data.model.response.GetGeneratedQuestionsResponseDto
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

data class QuestionAndAnswer(
    val question: String,
    val answer: String
)

data class GenerateQuestionUiState(
    val questionState: UiState = UiState.Empty,
    val errorMessage: String = "",

    val questionAndAnswerList: MutableList<QuestionAndAnswer> = mutableListOf(
        QuestionAndAnswer("질문0", "답변0"),
        QuestionAndAnswer("질문1", "답변1"),
        QuestionAndAnswer("질문2", "답변2"),
        QuestionAndAnswer("질문3", "답변3"),
        QuestionAndAnswer("질문4", "답변4"),
    ),
    val expectation: String = "",
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

    fun getGeneratedQuestions(generatedQuestionsRequestDto: GetGeneratedQuestionsRequestDto) {
        viewModelScope.launch {
            fileRepository.getGeneratedQuestions(generatedQuestionsRequestDto)
                .onStart { _generateQuestionUiState.update { it.copy(questionState = UiState.Loading) } }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        _generateQuestionUiState.update {
                            it.copy(
                                questionState = UiState.Success,
                                questionAndAnswerList = questionListDataAdapter(result.data)
                            )
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

    fun updateExpectation(input: String) {
        _generateQuestionUiState.update {
            it.copy(
                expectation = input
            )
        }
    }

    fun toggleQuestionSaveStatus(questionIndex: Int) {
        val currentValue = generateQuestionUiState.value.questionSaveStatus[questionIndex]
        _generateQuestionUiState.value.questionSaveStatus[questionIndex] = !currentValue
    }

    fun postFileQuestion(documentId: Int) {
        for (i: Int in 0..4) {
            val tempData = generateQuestionUiState.value.questionAndAnswerList[i]
            val tempQuestion = tempData.question
            val tempAnswer = tempData.answer

            if (generateQuestionUiState.value.questionSaveStatus[i]) {
                postFileQuestionRequest(
                    documentId = documentId,
                    postFileQuestionRequestDto = PostFileQuestionRequestDto(
                        tempQuestion,
                        tempAnswer
                    )
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

private fun questionListDataAdapter(responseQuestionAndAnswer: List<GetGeneratedQuestionsResponseDto.QuestionAndAnswer>): MutableList<QuestionAndAnswer> {
    val questionAndAnswerList: MutableList<QuestionAndAnswer> = mutableListOf()

    responseQuestionAndAnswer.forEach {
        questionAndAnswerList.add(
            QuestionAndAnswer(it.question, it.answer)
        )
    }

    return questionAndAnswerList
}