package com.fourever.forever.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R

private const val MAX_PROGRESS_BAR_SIZE = 380

@Composable
fun ProgressIndicator(progress: Int = 1, questionListSize: Int = 5) {
    val progressSize = (MAX_PROGRESS_BAR_SIZE * progress) / questionListSize
    Box {
        Divider(
            modifier = Modifier
                .width(MAX_PROGRESS_BAR_SIZE.dp),
            thickness = 5.dp,
            color = colorResource(id = R.color.gray_light)
        )
        Divider(
            modifier = Modifier
                .width(progressSize.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.purple_strong),
                            colorResource(id = R.color.blue_strong),
                        )
                    )
                ),
            thickness = 5.dp,
            color = Color.Transparent,
        )

    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun PrograssPreview() {
    MaterialTheme {
        ProgressIndicator(2)
    }
}