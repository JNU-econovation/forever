package com.fourever.forever.presentation.getsummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.SCREEN_MARGIN
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_PEEK_HEIGHT
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_RADIUS
import com.fourever.forever.presentation.component.btmsheet.QuestionListBtnSheet
import com.fourever.forever.ui.theme.foreverTypography

private const val SPACE_BETWEEN_TITLE_AND_CONTENT = 50
private const val CONTENT_AREA_HEIGHT = 550

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetSummaryScreen(
    summaryUiState: SummaryUiState,
    questionListUiState: QuestionListUiState,
    getFileList: () -> Unit,
    getQuestionList: () -> Unit
) {
    LaunchedEffect(Unit) {
        getFileList()
        getQuestionList()
    }

    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier.padding(horizontal = SCREEN_MARGIN.dp)
            ) {
                QuestionListBtnSheet(questionListUiState)
            }
        },
        sheetPeekHeight = BTM_SHEET_PEEK_HEIGHT.dp,
        sheetShape = RoundedCornerShape(
            topStart = BTM_SHEET_RADIUS.dp,
            topEnd = BTM_SHEET_RADIUS.dp
        ),
        sheetContainerColor = colorResource(id = R.color.white)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.purple_light),
                            colorResource(id = R.color.blue_light),
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SCREEN_MARGIN.dp)
            ) {
                Column {
                    Text(
                        text = summaryUiState.title,
                        style = foreverTypography.titleLarge,
                        color = colorResource(id = R.color.paragraph)
                    )
                    Text(
                        text = stringResource(id = R.string.summary_subtitle),
                        style = foreverTypography.bodyLarge,
                        color = colorResource(id = R.color.paragraph)
                    )
                }
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .height(
                            height = CONTENT_AREA_HEIGHT.dp
                        )
                ) {
                    Text(
                        text = summaryUiState.summary,
                        style = foreverTypography.bodyMedium,
                        color = colorResource(id = R.color.paragraph)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun SummaryPreview() {
    MaterialTheme {
        GetSummaryScreen(
            summaryUiState = SummaryUiState(),
            questionListUiState = QuestionListUiState(),
            getFileList = {},
            getQuestionList = {}
        )
    }
}