package com.fourever.forever.presentation.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fourever.forever.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileNameTopAppBar(fileName: String) {
    TopAppBar(
        title = {
            Text(
                text = fileName,
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(
                    id = R.color.paragraph
                )
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_top_navigation),
                contentDescription = stringResource(
                    id = R.string.description_ic_question_top_navigation
                )
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopPreview() {
    MaterialTheme {
        FileNameTopAppBar("프로그래밍 언어론 ch03a")
    }
}