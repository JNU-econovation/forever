package com.fourever.forever.presentation.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.fourever.forever.R

@Composable
fun ForeverCircularProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(30.dp),
        color = colorResource(id = R.color.blue_strong),
        trackColor = colorResource(id = R.color.gray_medium),
    )
}