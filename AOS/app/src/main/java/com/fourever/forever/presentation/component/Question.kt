package com.fourever.forever.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.util.abbreviateTextWithEllipsis

private const val BOX_HEIGHT = 73
private const val BOX_WIDTH = 360
private const val PADDING_HORIZONTAL = 20
private const val PADDING_VERTICAL = 25
private const val DIVIDER_THICKNESS = 1
private const val DIVIDER_WIDTH = 360
private const val MAX_QUESTION_LENGTH = 25
private const val SPACE_BETWEEN_LEADING_ICON_AND_TITLE = 15

@Composable
fun Question(question: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider(
            modifier = Modifier.width(DIVIDER_WIDTH.dp),
            color = colorResource(R.color.secondary_medium),
            thickness = DIVIDER_THICKNESS.dp
        )
        Box(
            modifier = Modifier
                .size(width = BOX_WIDTH.dp, height = BOX_HEIGHT.dp)
                .background(color = colorResource(id = R.color.white))
                .padding(horizontal = PADDING_HORIZONTAL.dp, vertical = PADDING_VERTICAL.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_btmsht_question),
                        contentDescription = ""
                    )
                    Spacer(
                        modifier = Modifier.size(SPACE_BETWEEN_LEADING_ICON_AND_TITLE.dp)
                    )
                    Text(
                        text = abbreviateTextWithEllipsis(question, MAX_QUESTION_LENGTH),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_enter),
                    contentDescription = ""
                )
            }
        }
        Divider(
            modifier = Modifier.width(DIVIDER_WIDTH.dp),
            color = colorResource(R.color.secondary_medium),
            thickness = DIVIDER_THICKNESS.dp
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun QuestionPreview() {
    MaterialTheme {
        Question("캡스톤 강의의 주요 일정은 어떻게 되어 있나요?")
    }
}
