package com.fourever.forever.presentation.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fourever.forever.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileUploadTopAppBar() {
    TopAppBar(
        title = {},
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_top_navigation),
                contentDescription = stringResource(
                    id = R.string.description_ic_file_upload_top_navigation
                )
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopPreview() {
    MaterialTheme {
        FileUploadTopAppBar()
    }
}