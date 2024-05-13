package com.fourever.forever.navigation


object ForeverDestinationArgs {
    const val DOCUMENT_ID_ARG = "documentId"
    const val FIRST_QUESTION_ID_ARG = "firstQuestionId"
    const val QUESTION_ID_ARG = "questionId"
    const val FILE_NAME_ARG = "fileName"
    const val FILE_URI_ARG = "fileUri"
    const val QUESTION_SIZE_ARG = "questionSize"
}

sealed class Screen(val route: String) {
    object Home : Screen("Home")


    object Generation : Screen("Generation")
    object UploadFile : Screen("UploadFile")
    object GenerateSummary :
        Screen("GenerateSummary/{${ForeverDestinationArgs.FILE_NAME_ARG}}/{${ForeverDestinationArgs.FILE_URI_ARG}}") {
        fun createRoute(fileName: String, fileUri: String) = "GenerateSummary/$fileName/$fileUri"
    }

    object GenerateQuestion :
        Screen("GenerateQuestion/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}") {
        fun createRoute(documentId: Int) = "GenerateQuestion/$documentId"
    }


    object Detail : Screen("Detail")
    object GetSummary : Screen("GetSummary/{${ForeverDestinationArgs.DOCUMENT_ID_ARG}}") {
        fun createRoute(documentId: Int) = "GetSummary/$documentId"
    }

    object GetSingleQuestion : Screen(
        "GetQuestion" +
                "/{${ForeverDestinationArgs.QUESTION_ID_ARG}}" +
                "/{${ForeverDestinationArgs.FILE_NAME_ARG}}"
    ) {
        fun createRoute(
            questionId: Int,
            fileName: String,
        ) = "GetQuestion/$questionId/$fileName"
    }

    object GetAllQuestions : Screen(
        "GetQuestion" +
                "/{${ForeverDestinationArgs.FIRST_QUESTION_ID_ARG}}" +
                "/{${ForeverDestinationArgs.FILE_NAME_ARG}}" +
                "/{${ForeverDestinationArgs.QUESTION_SIZE_ARG}}"
    ) {
        fun createRoute(
            firstQuestionId: Int,
            fileName: String,
            questionSize: Int
        ) = "GetQuestion/$firstQuestionId/$fileName/$questionSize"
    }
}
