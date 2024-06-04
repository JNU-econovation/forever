package com.fourever.forever.presentation.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
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
import com.fourever.forever.presentation.util.abbreviateText
import com.fourever.forever.ui.theme.foreverTypography

private const val DIVIDER_THICKNESS = 1
private const val DIVIDER_WIDTH = 380

private const val ANSWER_CARD_HORIZONTAL_PADDING = 10
private const val ANSWER_CARD_VERTICAL_PADDING = 20
private const val ANSWER_CARD_SPACE_BETWEEN_TITLE_AND_CONTENT = 10
private const val ANSWER_CARD_CONTENT_MAX_LENGTH = 1000

private const val ANSWER_CARD_CONTENT_WIDTH = 330
private const val ANSWER_CARD_CONTENT_HEIGHT = 40

@Composable
fun AnswerCard(answer: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier.width(DIVIDER_WIDTH.dp),
            color = colorResource(R.color.secondary_medium),
            thickness = DIVIDER_THICKNESS.dp
        )
        Column(
            modifier = Modifier
                .padding(horizontal = ANSWER_CARD_HORIZONTAL_PADDING.dp, vertical = ANSWER_CARD_VERTICAL_PADDING.dp),
        ) {
            Text(
                text = stringResource(id = R.string.question_bottom_answer),
                style = foreverTypography.titleSmall
            )
            Spacer(modifier = Modifier.size(ANSWER_CARD_SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
            Text(
                text = abbreviateText(answer, ANSWER_CARD_CONTENT_MAX_LENGTH),
                style = foreverTypography.bodySmall,
                modifier = Modifier
                    .width(ANSWER_CARD_CONTENT_WIDTH.dp)
            )
        }
        Divider(
            modifier = Modifier.width(DIVIDER_WIDTH.dp),
            color = colorResource(R.color.secondary_medium),
            thickness = DIVIDER_THICKNESS.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CardPreview() {
    MaterialTheme {
        AnswerCard("이것이 질문입니다.이것이 질문입니다.이것이 질문입니다.이것이 질문입니다.이것이 질문입니다.")
    }
}
