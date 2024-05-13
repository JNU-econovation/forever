package com.fourever.forever.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class ForeverNavActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(Screen.Home.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToUploadFile() {
        navController.navigate(Screen.UploadFile.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToSummaryGeneration(fileName: String, fileUri: String) {
        navController.navigate(Screen.GenerateSummary.createRoute(fileName, fileUri)) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToQuestionGeneration() {
        navController.navigate(Screen.GenerateQuestion.route) {
            popUpTo(Screen.GenerateSummary.route)
            launchSingleTop = false
        }
    }

    fun navigateToGetSummary(documentId: Int) {
        navController.navigate(Screen.GetSummary.createRoute(documentId)) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToGetSingleQuestion(questionId: Int, fileName: String) {
        navController.navigate(
            Screen.GetSingleQuestion.createRoute(questionId, fileName)
        ) {
            popUpTo(Screen.GetSummary.route)
            launchSingleTop = false
        }
    }

    fun navigateToGetAllQuestion(firstQuestionId: Int, fileName: String, questionSize: Int) {
        navController.navigate(
            Screen.GetAllQuestions.createRoute(firstQuestionId, fileName, questionSize)
        ) {
            popUpTo(Screen.GetSummary.route)
            launchSingleTop = false
        }
    }
}