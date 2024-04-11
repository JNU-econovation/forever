package com.fourever.forever.presentation.util

sealed class UiState {
    object Success : UiState()
    object Empty : UiState()
    object Failure : UiState()
    object Loading : UiState()
}