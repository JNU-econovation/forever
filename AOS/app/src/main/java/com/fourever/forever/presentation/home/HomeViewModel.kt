package com.fourever.forever.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourever.forever.data.ResultWrapper
import com.fourever.forever.data.model.response.GetFileListResponseDto
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


data class HomeUiState(
    val fileState: UiState = UiState.Empty,
    val errorMessage: String = "",
    val files: List<GetFileListResponseDto.Document> = listOf()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fileRepository: FileRepository
): ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        getFileList(page = 0)
    }

    fun getFileList(page: Int) {
        viewModelScope.launch {
            fileRepository.getFileList(page)
                .onStart { _homeUiState.update { it.copy(fileState = UiState.Loading) } }
                .collect { result ->
                    if (result is ResultWrapper.Success) {
                        result.data.let { result ->
                            val oldList = _homeUiState.value.files
                            val newList = result.data?.documents ?: listOf()
                            val joinedList: MutableList<GetFileListResponseDto.Document> = ArrayList()
                            joinedList.addAll(oldList)
                            joinedList.addAll(newList)

                            _homeUiState.update {
                                it.copy(
                                    fileState = UiState.Success,
                                    files = joinedList
                                )
                            }
                        }
                    } else if (result is ResultWrapper.Error) {
                        _homeUiState.update {
                            it.copy(
                                fileState = UiState.Failure,
                                errorMessage = result.errorMessage,
                                files = listOf()
                            )
                        }
                    }
                }
        }
    }
}