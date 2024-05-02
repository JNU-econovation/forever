package com.fourever.forever.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fourever.forever.presentation.fileupload.FileUploadScreen
import com.fourever.forever.presentation.fileupload.FileUploadViewModel
import com.fourever.forever.presentation.generatequestion.GenerateQuestionScreen
import com.fourever.forever.presentation.generatesummary.GenerateSummaryScreen

fun NavGraphBuilder.generationGraph() {
    navigation(
        startDestination = Screen.UploadFile.route, route = Screen.Generation.route
    ) {
        composable(Screen.UploadFile.route) {
            val fileUploadViewModel = hiltViewModel<FileUploadViewModel>()
            val fileUploadUiState by fileUploadViewModel.fileUploadUiState.collectAsState()

            FileUploadScreen(
                fileUploadUiState = fileUploadUiState,
                updateFileChosenState = { isFileChosen ->
                    (fileUploadViewModel::updateFileChosenState)(isFileChosen)
                },
                updateFileName = { fileName ->
                    (fileUploadViewModel::updateFileName)(fileName)
                },
                updateFileUri = { fileUri ->
                    (fileUploadViewModel::updateFileUri)(fileUri)
                },
                navigateToGenerateSummary = {}
            )
        }
        composable(Screen.GenerateSummary.route) {
            GenerateSummaryScreen("", "")
        }
        composable(Screen.GenerateQuestion.route) {
            GenerateQuestionScreen()
        }
    }
}