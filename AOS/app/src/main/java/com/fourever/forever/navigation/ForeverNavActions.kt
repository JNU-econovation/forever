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

    fun navigateToSummaryGeneration() {
        navController.navigate(Screen.GenerateSummary.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToQuestionGeneration(questionIndex: Int) {
        navController.navigate(Screen.GenerateQuestion.route) {
            popUpTo(Screen.GenerateSummary.route)
            launchSingleTop = false
        }
    }

    fun navigateToGetSummary() {
        navController.navigate(Screen.GetSummary.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun navigateToGetQuestion() {
        navController.navigate(Screen.GetQuestion.route) {
            popUpTo(Screen.GetSummary.route)
            launchSingleTop = false
        }
    }
}