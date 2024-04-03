package com.fourever.forever.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fourever.forever.presentation.fileupload.FileUploadScreen
import com.fourever.forever.presentation.generatequestion.GenerateQuestionScreen
import com.fourever.forever.presentation.generatesummary.GenerateSummaryScreen

fun NavGraphBuilder.generationGraph(navController: NavController) {
    navigation(
        startDestination = Screen.UploadFile.route, route = Screen.Generation.route
    ) {
        composable(Screen.UploadFile.route) {
            FileUploadScreen()
        }
        composable(Screen.GenerateSummary.route) {
            GenerateSummaryScreen("", "")
        }
        composable(Screen.GenerateQuestion.route) {
            GenerateQuestionScreen()
        }
    }
}