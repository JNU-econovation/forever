package com.fourever.forever.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fourever.forever.presentation.getquestion.GetSingleQuestionScreen
import com.fourever.forever.presentation.getsummary.GetSummaryScreen
import com.fourever.forever.presentation.getsummary.GetSummaryViewModel

fun NavGraphBuilder.detailGraph() {
    navigation(
        startDestination = Screen.GetSummary.route, route = Screen.Detail.route
    ) {
        composable(
            Screen.GetSummary.route,
            arguments = listOf(
                navArgument(ForeverDestinationArgs.DOCUMENT_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val getSummaryViewModel = hiltViewModel<GetSummaryViewModel>()
            val summaryUiState by getSummaryViewModel.summaryUiState.collectAsState()
            val questionListUiState by getSummaryViewModel.questionListUiState.collectAsState()

            val documentId = it.arguments?.getInt(ForeverDestinationArgs.DOCUMENT_ID_ARG) ?: 0

            GetSummaryScreen(
                summaryUiState = summaryUiState,
                questionListUiState = questionListUiState,
                getFileList = { (getSummaryViewModel::getSummary)(documentId) },
                getQuestionList = { (getSummaryViewModel::getQuestionList)(documentId) }
            )
        }
        composable(
            Screen.GetSingleQuestion.route,
            arguments = listOf(
                navArgument(ForeverDestinationArgs.DOCUMENT_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(ForeverDestinationArgs.QUESTION_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(ForeverDestinationArgs.FILE_NAME_ARG) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            GetSingleQuestionScreen()
        }

        composable(
            Screen.GetAllQuestions.route,
            arguments = listOf(
                navArgument(ForeverDestinationArgs.DOCUMENT_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(ForeverDestinationArgs.FILE_NAME_ARG) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            GetSingleQuestionScreen()
        }
    }
}