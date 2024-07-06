package com.fourever.forever.presentation.generatesummary

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.SCREEN_MARGIN
import com.fourever.forever.presentation.component.buttons.ShortColorBtn
import com.fourever.forever.presentation.component.buttons.ShortWhiteBtn
import com.fourever.forever.presentation.util.UiState
import com.fourever.forever.presentation.util.abbreviateTextWithEllipsis
import com.fourever.forever.ui.theme.foreverTypography

private const val SPACE_BETWEEN_TITLE_AND_CONTENT = 50
private const val SPACE_BETWEEN_CONTENT_AND_BUTTONS = 20

@Composable
fun GenerateSummaryScreen(
    generateSummaryUiState: GenerateSummaryUiState,
    fileName: String,
    postFileSummary: () -> Unit,
    navigateUp: () -> Unit
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.purple_light),
                            colorResource(id = R.color.blue_light),
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SCREEN_MARGIN.dp)
            ) {
                Column {
                    Text(
                        text = abbreviateTextWithEllipsis(fileName, 15),
                        style = foreverTypography.titleLarge,
                        color = colorResource(id = R.color.paragraph)
                    )
                    Text(
                        text = stringResource(id = R.string.summary_subtitle),
                        style = foreverTypography.bodyLarge,
                        color = colorResource(id = R.color.paragraph)
                    )
                }
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
                SummaryContent(summary = generateSummaryUiState.summary, generateSummaryState = generateSummaryUiState.generateSummaryState)
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_CONTENT_AND_BUTTONS.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShortWhiteBtn(navigateUp)
                    ShortColorBtn(postFileSummary, generateSummaryUiState.generateSummaryState == UiState.Success)
                }
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun SummaryPreview() {
    MaterialTheme {
        GenerateSummaryScreen(
            generateSummaryUiState = GenerateSummaryUiState(),
            fileName = "프로그래밍 언어론_ch03a",
            postFileSummary = {},
            navigateUp = {}
        )
    }
}