package com.fourever.forever.navigation

import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fourever.forever.data.model.request.GetGeneratedQuestionsRequestDto
import com.fourever.forever.presentation.fileupload.FileUploadScreen
import com.fourever.forever.presentation.fileupload.FileUploadViewModel
import com.fourever.forever.presentation.generatequestion.GenerateQuestionScreen
import com.fourever.forever.presentation.generatequestion.GenerateQuestionViewModel
import com.fourever.forever.presentation.generatesummary.GenerateSummaryScreen
import com.fourever.forever.presentation.generatesummary.GenerateSummaryViewModel
import com.fourever.forever.presentation.util.getMultipartBody
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavGraphBuilder.generationGraph(
    navController: NavHostController,
    navActions: ForeverNavActions
) {
    navigation(
        startDestination = Screen.UploadFile.route, route = Screen.Generation.route
    ) {
        composable(
            Screen.UploadFile.route
        ) {
            val fileUploadViewModel = hiltViewModel<FileUploadViewModel>()
            val fileUploadUiState by fileUploadViewModel.fileUploadUiState.collectAsState()

            FileUploadScreen(
                fileUploadUiState = fileUploadUiState,
                updateFileChosenState = { isFileChosen ->
                    (fileUploadViewModel::updateFileChosenState)(isFileChosen)
                },
                updateFileName = { fileName ->
                    (fileUploadViewModel::updateFileName)(fileName)
                },
                updateFileUri = { fileUri ->
                    (fileUploadViewModel::updateFileUri)(fileUri)
                },
                navigateToGenerateSummary = {
                    val fileName = fileUploadUiState.fileName
                    val fileUri = fileUploadUiState.fileUri
                    val encodedUri = if (Build.VERSION.SDK_INT >= 33) {
                        URLEncoder.encode(fileUri.toString(), StandardCharsets.UTF_8)
                    } else {
                        URLEncoder.encode(fileUri.toString())
                    }

                    navActions.navigateToSummaryGeneration(fileName, encodedUri)
                }
            )
        }

        composable(
            Screen.GenerateSummary.route,
            arguments = listOf(
                navArgument(ForeverDestinationArgs.FILE_NAME_ARG) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(ForeverDestinationArgs.FILE_URI_ARG) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val fileName = it.arguments?.getString(ForeverDestinationArgs.FILE_NAME_ARG) ?: ""
            val fileUriString = it.arguments?.getString(ForeverDestinationArgs.FILE_URI_ARG) ?: ""

            val fileUri = Uri.parse(fileUriString)
            val context = LocalContext.current
            val fileMultipartBody = getMultipartBody(fileUri, context, fileName)

            val generateSummaryViewModel = hiltViewModel<GenerateSummaryViewModel>()
            val generateSummaryUiState by generateSummaryViewModel.generateSummaryUiState.collectAsState()

            LaunchedEffect(Unit) {
                (generateSummaryViewModel::postPdfFile)(fileMultipartBody)
            }

            GenerateSummaryScreen(
                generateSummaryUiState = generateSummaryUiState,
                fileName = fileName,
                postFileSummary = {
                    (generateSummaryViewModel::postFileSummary)(fileName)
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "requestFileName",
                        value = generateSummaryViewModel.getGeneratedQuestionRequestDto.fileName
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "requestFilePath",
                        value = generateSummaryViewModel.getGeneratedQuestionRequestDto.filePath
                    )
                    Handler(Looper.getMainLooper()).postDelayed({
                        navActions.navigateToQuestionGeneration(
                            fileName,
                            generateSummaryUiState.documentId
                        )
                    }, 300)
                },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(
            Screen.GenerateQuestion.route,
            arguments = listOf(
                navArgument(ForeverDestinationArgs.FILE_NAME_ARG) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(ForeverDestinationArgs.DOCUMENT_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = 0
                },
            )
        ) {
            val fileName = it.arguments?.getString(ForeverDestinationArgs.FILE_NAME_ARG) ?: ""
            val documentId = it.arguments?.getInt(ForeverDestinationArgs.DOCUMENT_ID_ARG) ?: 0
            val requestFileName =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("requestFileName")
            val requestFilePath =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("requestFilePath")


            val generateQuestionViewModel = hiltViewModel<GenerateQuestionViewModel>()
            val generateQuestionUiState by generateQuestionViewModel.generateQuestionUiState.collectAsState()

            LaunchedEffect(Unit) {
                (generateQuestionViewModel::getGeneratedQuestions)(
                    GetGeneratedQuestionsRequestDto(
                        filePath = requestFilePath!!,
                        fileName = requestFileName!!
                    )
                )
            }

            GenerateQuestionScreen(
                generateQuestionUiState = generateQuestionUiState,
                fileName = fileName,
                navigateUp = { navController.navigateUp() },
                toggleQuestionSaveStatus = { questionIndex ->
                    generateQuestionViewModel.toggleQuestionSaveStatus(
                        questionIndex
                    )
                },
                navigateToHome = { navActions.navigateToHome() },
                postFileQuestion = { generateQuestionViewModel.postFileQuestion(documentId) },
                updateExpectation = { expectation ->
                    generateQuestionViewModel.updateExpectation(
                        expectation
                    )
                }
            )
        }
    }
}