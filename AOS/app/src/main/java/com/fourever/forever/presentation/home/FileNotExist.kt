package com.fourever.forever.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.fourever.forever.R
import com.fourever.forever.ui.theme.foreverTypography

@Composable
fun FileNotExist() {
    Text(
        text = stringResource(id = R.string.main_file_list_empty),
        style = foreverTypography.bodySmall,
        color = colorResource(id = R.color.placeholder)
    )
}