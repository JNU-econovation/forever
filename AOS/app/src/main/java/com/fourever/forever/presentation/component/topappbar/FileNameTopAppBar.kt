package com.fourever.forever.presentation.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fourever.forever.R
import com.fourever.forever.ui.theme.foreverTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileNameTopAppBar(
    fileName: String,
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = fileName,
                style = foreverTypography.titleMedium,
                color = colorResource(
                    id = R.color.paragraph
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_top_navigation),
                    contentDescription = stringResource(
                        id = R.string.description_ic_question_top_navigation
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopPreview() {
    MaterialTheme {
        FileNameTopAppBar(
            "프로그래밍 언어론 ch03a",
            {}
        )
    }
}