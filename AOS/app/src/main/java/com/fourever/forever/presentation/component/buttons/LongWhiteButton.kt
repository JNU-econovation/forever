package com.fourever.forever.presentation.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R

private const val SPACE_BETWEEN_ICON_AND_TEXT = 10
private const val ICON_SIZE = 15

@Composable
fun LongWhiteBtn(enabled: Boolean) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(width = LONG_BTN_WIDTH.dp, height = LONG_BTN_HEIGHT.dp),
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
            containerColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.secondary_strong),
            disabledContainerColor = colorResource(id = R.color.gray_light),
            disabledContentColor = colorResource(R.color.gray_medium)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (enabled) {
                Image(
                    painter = painterResource(id = R.drawable.ic_save_question_enabled),
                    contentDescription = null,
                    modifier = Modifier.size(ICON_SIZE.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_save_question_disabled),
                    contentDescription = null,
                    modifier = Modifier.size(ICON_SIZE.dp)
                )
            }
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_ICON_AND_TEXT.dp))
            Text(
                text = stringResource(id = R.string.save_question_button),
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BtnPreview() {
    MaterialTheme {
        LongWhiteBtn(enabled = false)
    }
}