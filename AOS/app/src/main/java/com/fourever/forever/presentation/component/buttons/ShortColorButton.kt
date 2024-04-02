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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.ui.theme.foreverTypography

@Composable
fun ShortColorBtn() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(width = SHORT_BTN_WIDTH.dp, height = SHORT_BTN_HEIGHT.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(id = R.color.purple_strong),
                        colorResource(id = R.color.blue_strong),
                    )
                )
            ),
        shape = RoundedCornerShape(BUTTON_RADIUS.dp),
        border = BorderStroke(
            width = BUTTON_STROKE_THICKNESS.dp,
            color = colorResource(id = R.color.secondary_strong)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.white),
        )
    ) {
        Text(
            text = stringResource(id = R.string.summary_save_button),
            style = foreverTypography.labelMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BtnPreview() {
    MaterialTheme {
        ShortColorBtn()
    }
}