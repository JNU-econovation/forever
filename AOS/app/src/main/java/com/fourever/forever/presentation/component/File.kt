package com.fourever.forever.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.util.abbreviateTextWithEllipsis
import com.fourever.forever.ui.theme.foreverTypography

private const val CARD_RADIUS = 10
private const val CARD_HEIGHT = 74
private const val CARD_WIDTH = 320
private const val CARD_PADDING = 25
private const val SPACE_BETWEEN_LEADING_ICON_AND_TITLE = 20
private const val MAX_TITLE_LENGTH = 15
private const val BORDER_WIDTH = 0.3

@Composable
fun File(fileName: String) {
    Card(
        shape = RoundedCornerShape(CARD_RADIUS.dp),
        modifier = Modifier
            .size(
                height = CARD_HEIGHT.dp,
                width = CARD_WIDTH.dp
            ),
        border = BorderStroke(
            width = BORDER_WIDTH.dp,
            color = colorResource(id = R.color.secondary_strong)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.purple_light),
                            colorResource(id = R.color.blue_light),
                        )
                    )
                )
                .padding(CARD_PADDING.dp),
            contentAlignment = Alignment.Center,

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
                        painter = painterResource(R.drawable.ic_main_file),
                        contentDescription = ""
                    )
                    Spacer(
                        modifier = Modifier.size(SPACE_BETWEEN_LEADING_ICON_AND_TITLE.dp)
                    )
                    Text(
                        text = abbreviateTextWithEllipsis(text = fileName, maxLength = MAX_TITLE_LENGTH),
                        style = foreverTypography.bodySmall
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_enter),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilePreview() {
    MaterialTheme {
        File("프로그래밍_언어론_ch03az")
    }
}
