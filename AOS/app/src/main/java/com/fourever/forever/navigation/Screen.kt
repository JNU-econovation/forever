package com.fourever.forever.navigation

object ForeverDestinationArgs {
    const val DOCUMENT_ID_ARG = "documentId"
    const val QUESTION_ID_ARG = "questionId"
    const val FILE_NAME_ARG = "fileName"
    const val QUESTION_SIZE_ARG = "questionSize"
}

sealed class Screen(val route: String) {
    object Home : Screen("Home")


    object Generation : Screen("Generation")
    object UploadFile : Screen("UploadFile")
    object GenerateSummary : Screen("GenerateSummary/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}") {
        fun createRoute(documentId: Int) = "GenerateSummary/$documentId"
    }
    object GenerateQuestion :
        Screen("GenerateQuestion/{${ForeverDestinationArgs.QUESTION_ID_ARG}}") {
        fun createRoute(questionIndex: Int) = "GenerateQuestion/$questionIndex"
    }


    object Detail : Screen("Detail")
    object GetSummary : Screen("GetSummary/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}") {
        fun createRoute(documentId: Int) = "GetSummary/$documentId"
    }
    object GetSingleQuestion : Screen(
        "GetQuestion" +
                "/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}" +
                "/{${ForeverDestinationArgs.QUESTION_ID_ARG}}" +
                "/{${ForeverDestinationArgs.FILE_NAME_ARG}}"
    ) {
        fun createRoute(
            documentId: Int,
            questionId: Int,
            fileName: String,
        ) = "GetQuestion/$documentId/$questionId/$fileName"
    }
    object GetAllQuestions : Screen(
        "GetQuestion" +
                "/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}" +
                "/{${ForeverDestinationArgs.FILE_NAME_ARG}}" +
                "/{${ForeverDestinationArgs.QUESTION_SIZE_ARG}}"
    ) {
        fun createRoute(
            documentId: Int,
            fileName: String,
            questionSize: Int
        ) = "GetQuestion/$documentId/$fileName/$questionSize"
    }
}
