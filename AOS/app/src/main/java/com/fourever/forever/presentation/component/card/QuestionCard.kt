package com.fourever.forever.presentation.component.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.util.abbreviateText
import com.fourever.forever.ui.theme.foreverTypography

@Composable
fun QuestionCard(question: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.paragraph)
        ),
        modifier = Modifier
            .width(CARD_WIDTH.dp)
            .border(
                width = CARD_STROKE_THICKNESS.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.purple_medium),
                        colorResource(id = R.color.blue_medium),
                    )
                ),
                shape = RoundedCornerShape(CARD_RADIUS.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = CARD_HORIZONTAL_PADDING.dp, vertical = CARD_VERTICAL_PADDING.dp),
        ) {
            Text(
                text = stringResource(id = R.string.question),
                style = foreverTypography.titleSmall
            )
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
            Text(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                text = abbreviateText(question, CARD_CONTENT_MAX_LENGTH),
                style = foreverTypography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardPreview() {
    MaterialTheme {
        QuestionCard("이것이 질문입니다.이것이 질문입니다.이것이 질문입니다.이것이 질문입니다.이것이 질문입니다.")
    }
}