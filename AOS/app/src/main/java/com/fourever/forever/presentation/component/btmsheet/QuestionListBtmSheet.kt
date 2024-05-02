package com.fourever.forever.presentation.component.btmsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.getsummary.QuestionList
import com.fourever.forever.presentation.getsummary.QuestionListUiState
import com.fourever.forever.ui.theme.foreverTypography

@Composable
fun QuestionListBtnSheet(
    questionListUiState: QuestionListUiState,
    onQuestionClick: (Int) -> Unit,
    onAllQuestionBtnClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(BTM_SHEET_CONTENT_WIDTH.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.question_list_title),
                style = foreverTypography.titleMedium,
                color = colorResource(id = R.color.paragraph)
            )
            Spacer(modifier = Modifier.size(BTM_SHEET_SPACE_BETWEEN_TITLE_AND_SUBTITLE.dp))
            Text(
                text = stringResource(id = R.string.question_list_subtitle),
                style = foreverTypography.bodySmall,
                color = colorResource(id = R.color.subtitle)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuestionList(
                questionList = questionListUiState.questionList,
                onQuestionClick = { questionId -> onQuestionClick(questionId) },
                onAllQuestionBtnClick = { onAllQuestionBtnClick() }
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun BtmSheetPreview() {
    MaterialTheme {
        QuestionListBtnSheet(
            questionListUiState = QuestionListUiState(),
            onQuestionClick = { _ -> },
            onAllQuestionBtnClick = {}
        )
    }
}