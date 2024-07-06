package com.fourever.forever.presentation.generatesummary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.component.ForeverCircularProgressIndicator
import com.fourever.forever.presentation.util.UiState
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText

private const val CONTENT_AREA_HEIGHT = 500

@Composable
fun SummaryContent(summary: String, generateSummaryState: UiState) {
    when(generateSummaryState) {
        UiState.Empty -> OnContentEmpty()
        UiState.Loading -> OnSummaryLoading()
        UiState.Success -> OnGetSummary(summary = summary)
        UiState.Failure -> OnFailure()
    }
}

@Composable
private fun OnContentEmpty() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = CONTENT_AREA_HEIGHT.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ForeverCircularProgressIndicator()
    }
}

@Composable
private fun OnSummaryLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = CONTENT_AREA_HEIGHT.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(30.dp),
            color = colorResource(id = R.color.blue_strong),
            trackColor = colorResource(id = R.color.gray_medium),
        )
    }
}

@Composable
private fun OnGetSummary(summary: String) {
    Column(
        modifier = Modifier
            .height(
                height = CONTENT_AREA_HEIGHT.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        RichText {
            Markdown(content = summary)
        }
    }
}

@Composable
private fun OnFailure() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = CONTENT_AREA_HEIGHT.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

    }
}