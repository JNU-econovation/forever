package com.fourever.forever.presentation.getquestion

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.SCREEN_MARGIN
import com.fourever.forever.presentation.component.ProgressIndicator
import com.fourever.forever.presentation.component.btmsheet.AnswerBtmSheet
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_PEEK_HEIGHT
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_RADIUS
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_SHADOW_ELEVATION
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_TONAL_ELEVATION
import com.fourever.forever.presentation.component.buttons.LongColorBtn
import com.fourever.forever.presentation.component.card.QuestionCard
import com.fourever.forever.presentation.component.topappbar.FileNameTopAppBar

private const val SPACE_BETWEEN_CARD_AND_BUTTON = 30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllQuestionScreen(
    allQuestionUiState: AllQuestionUiState,
    fileName: String,
    questionSize: Int,
    getQuestion: (Int) -> Unit,
    navigateUpToSummary: () -> Unit,
    navigateUpToPrevQuestion: () -> Unit,
    updateQuestionIndex: () -> Unit
) {
    val backPressedState by remember { mutableStateOf(true) }

    BackHandler(enabled = backPressedState) {
        if (allQuestionUiState.questionIndex == 1) {
            navigateUpToSummary()
        } else {
            navigateUpToPrevQuestion()
        }
    }

    LaunchedEffect(Unit) {
        getQuestion(allQuestionUiState.questionIndex)
    }

    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier.padding(horizontal = SCREEN_MARGIN.dp)
            ) {
                AnswerBtmSheet(answer = allQuestionUiState.answer)
            }
        },
        topBar = {
            FileNameTopAppBar(
                fileName = fileName,
                onBackButtonClick = navigateUpToSummary
            )
        },
        sheetPeekHeight = BTM_SHEET_PEEK_HEIGHT.dp,
        sheetShape = RoundedCornerShape(
            topStart = BTM_SHEET_RADIUS.dp,
            topEnd = BTM_SHEET_RADIUS.dp
        ),
        sheetContainerColor = colorResource(id = R.color.white),
        sheetTonalElevation = BTM_SHEET_TONAL_ELEVATION.dp,
        sheetShadowElevation = BTM_SHEET_SHADOW_ELEVATION.dp
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = SCREEN_MARGIN.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressIndicator(
                progress = allQuestionUiState.questionIndex,
                questionListSize = questionSize
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuestionCard(question = allQuestionUiState.question)
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_CARD_AND_BUTTON.dp))
                LongColorBtn(
                    text = if (allQuestionUiState.questionIndex == questionSize) {
                        stringResource(id = R.string.question_done_button)
                    } else {
                           String.format(
                               stringResource(R.string.question_progress_button),
                               allQuestionUiState.questionIndex,
                               questionSize
                           )
                    },
                    enabled = true,
                    onClick = {
                        if (allQuestionUiState.questionIndex == questionSize) {
                            navigateUpToSummary()
                        } else {
                            getQuestion(allQuestionUiState.questionIndex)
                            updateQuestionIndex()
                        }
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun QuestionPreview() {
    MaterialTheme {
        GetAllQuestionScreen(
            allQuestionUiState = AllQuestionUiState(),
            fileName = "",
            questionSize = 1,
            getQuestion = {},
            navigateUpToSummary = {},
            navigateUpToPrevQuestion = {},
            updateQuestionIndex = {}
        )
    }
}