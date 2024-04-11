package com.fourever.forever.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fourever.forever.presentation.getquestion.GetQuestionScreen
import com.fourever.forever.presentation.getsummary.GetSummaryScreen

fun NavGraphBuilder.detailGraph(navController: NavController) {
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
            val documentId = it.arguments?.getBoolean(ForeverDestinationArgs.DOCUMENT_ID_ARG) ?: 0

            GetSummaryScreen("", "")
        }
        composable(
            Screen.GetQuestion.route,
            arguments = listOf(
                navArgument(ForeverDestinationArgs.DOCUMENT_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument("fileName") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            GetQuestionScreen()
        }
    }
}