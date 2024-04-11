package com.fourever.forever.navigation

object ForeverDestinationArgs {
    const val DOCUMENT_ID_ARG = "documentId"
    const val QUESTION_ID_ARG = "questionId"
}

sealed class Screen(val route: String) {
    object Home: Screen("Home")

    object Generation: Screen("Generation")
    object UploadFile: Screen("UploadFile")
    object GenerateSummary: Screen("GenerateSummary/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}") {
        fun createRoute(documentId: Int) = "GenerateSummary/$documentId"
    }
    object GenerateQuestion: Screen("GenerateQuestion/{${ForeverDestinationArgs.QUESTION_ID_ARG}}") {
        fun createRoute(questionIndex: Int) = "GenerateQuestion/$questionIndex"
    }


    object Detail: Screen("Detail")
    object GetSummary: Screen("GetSummary/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}") {
        fun createRoute(documentId: Int) = "GetSummary/$documentId"
    }
    object GetQuestion: Screen("GetQuestion/{${ForeverDestinationArgs.QUESTION_ID_ARG}}") {
        fun createRoute(questionIndex: Int) = "GetQuestion/$questionIndex"
    }
}
