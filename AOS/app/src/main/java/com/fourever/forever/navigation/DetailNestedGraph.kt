package com.fourever.forever.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fourever.forever.presentation.getquestion.GetQuestionScreen
import com.fourever.forever.presentation.getsummary.GetSummaryScreen

fun NavGraphBuilder.detailGraph(navController: NavController) {
    navigation(
        startDestination = Screen.GetSummary.route, route = Screen.Detail.route
    ) {
        composable(Screen.GetSummary.route) {
            GetSummaryScreen("", "")
        }
        composable(Screen.GetQuestion.route) {
            GetQuestionScreen()
        }
    }
}