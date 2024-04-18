package com.fourever.forever.presentation.getsummary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.data.model.response.GetQuestionListDto
import com.fourever.forever.presentation.component.DIVIDER_THICKNESS
import com.fourever.forever.presentation.component.DIVIDER_WIDTH
import com.fourever.forever.presentation.component.Question
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_CONTENT_VERTICAL_PADDING
import com.fourever.forever.presentation.component.btmsheet.BTM_SHEET_SPACE_BETWEEN_TITLE_AND_CONTENT
import com.fourever.forever.presentation.component.buttons.LongColorBtn

@Composable
fun QuestionList(
    questionList: List<GetQuestionListDto.Questions> = listOf(),
    onQuestionClick: (Int) -> Unit,
    onAllQuestionBtnClick: () -> Unit
) {
    if (questionList.isNotEmpty()) {
        Spacer(modifier = Modifier.size(BTM_SHEET_SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
        LazyColumn {
            items(questionList) { question ->
                Question(
                    question = question.content,
                    onQuestionClick = { onQuestionClick(question.questionId) }
                )
            }
        }
        Divider(
            modifier = Modifier.width(DIVIDER_WIDTH.dp),
            color = colorResource(R.color.secondary_medium),
            thickness = DIVIDER_THICKNESS.dp
        )
        Spacer(modifier = Modifier.height(BTM_SHEET_CONTENT_VERTICAL_PADDING.dp))
        LongColorBtn(
            text = stringResource(id = R.string.question_list_check_question_button),
            enabled = true,
            onClick = { onAllQuestionBtnClick() }
        )
        Spacer(modifier = Modifier.height(BTM_SHEET_CONTENT_VERTICAL_PADDING.dp))
    } else {
        Column(
            modifier = Modifier.size(width = 380.dp, height = 350.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            QuestionNotExist()
        }
    }
}