package com.fourever.forever.presentation.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R

@Composable
fun LongColorBtn(text: String, enabled: Boolean) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(width = LONG_BTN_WIDTH.dp, height = LONG_BTN_HEIGHT.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(id = R.color.purple_medium),
                        colorResource(id = R.color.blue_medium),
                    )
                )
            ),
        enabled = enabled,
        shape = RoundedCornerShape(BUTTON_RADIUS.dp),
        border = if (enabled) {
            BorderStroke(
                width = BUTTON_STROKE_THICKNESS.dp,
                color = colorResource(id = R.color.secondary_strong)
            )
        } else {
            null
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.white),
            disabledContainerColor = colorResource(id = R.color.gray_light),
            disabledContentColor = colorResource(R.color.gray_medium)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BtnPreview() {
    MaterialTheme {
        LongColorBtn("자료 요약하기", true)
    }
}