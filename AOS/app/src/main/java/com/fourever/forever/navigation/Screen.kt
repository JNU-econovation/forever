package com.fourever.forever.navigation

sealed class Screen(val route: String) {
    object Home: Screen("Home")

    object Generation: Screen("Generation")
    object UploadFile: Screen("UploadFile")
    object GenerateSummary: Screen("GeneratedSummary")
    object GenerateQuestion: Screen("GeneratedQuestion/{questionIndex}") {
        fun createRoute(questionIndex: Int) = "GeneratedQuestion/$questionIndex"
    }


    object Detail: Screen("Detail")
    object GetSummary: Screen("GetSummary")
    object GetQuestion: Screen("GetQuestion/{questionIndex}") {
        fun createRoute(questionIndex: Int) = "GetQuestion/$questionIndex"
    }
}
