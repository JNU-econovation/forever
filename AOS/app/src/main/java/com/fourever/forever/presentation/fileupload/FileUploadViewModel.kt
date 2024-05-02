package com.fourever.forever.presentation.fileupload

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FileUploadUiState(
    val isFileChosen: Boolean = false,
    val fileName: String = "",
    val fileUri: Uri = Uri.EMPTY
)

class FileUploadViewModel : ViewModel() {
    private val _fileUploadUiState = MutableStateFlow(FileUploadUiState())
    val fileUploadUiState = _fileUploadUiState.asStateFlow()

    fun updateFileChosenState(fileChosenState: Boolean) {
        _fileUploadUiState.update {
            it.copy(
                isFileChosen = fileChosenState
            )
        }
    }

    fun updateFileName(fileName: String) {
        _fileUploadUiState.update {
            it.copy(
                fileName = fileName
            )
        }
    }

    fun updateFileUri(fileUri: Uri) {
        _fileUploadUiState.update {
            it.copy(
                fileUri = fileUri
            )
        }
    }
}