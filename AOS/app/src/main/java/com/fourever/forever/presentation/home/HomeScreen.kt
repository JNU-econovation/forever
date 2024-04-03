package com.fourever.forever.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fourever.forever.R
import com.fourever.forever.presentation.SCREEN_MARGIN
import com.fourever.forever.presentation.component.topappbar.MainTopAppBar
import com.fourever.forever.ui.theme.foreverTypography

private const val SPACE_BETWEEN_TITLE_AND_SUBTITLE = 2
private const val SPACE_BETWEEN_TITLE_AND_FILE_LIST = 100

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { MainTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, shape = CircleShape) {
                Image(
                    painter = painterResource(id = R.drawable.btn_main_add_file),
                    contentDescription = stringResource(
                        id = R.string.description_btn_main_add_file
                    ),
                    contentScale = ContentScale.Fit
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = SCREEN_MARGIN.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.main_title),
                    style = foreverTypography.titleMedium,
                    color = colorResource(id = R.color.paragraph)
                )
                Text(
                    text = "\uD83D\uDCC2",
                    fontSize = 45.sp
                )
            }
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_SUBTITLE.dp))
            Text(
                text = stringResource(id = R.string.main_subtitle),
                style = foreverTypography.labelSmall,
                color = colorResource(id = R.color.gray_medium)
            )
            /**
             * TODO
             * if (fileNotExist) { FileNotExist() }
             * else { FileList() }
             */
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_FILE_LIST.dp))
            FileList(
                fileList = listOf(
                    "프로그래밍_언어론_ch03az",
                    "프로그래밍_언어론_ch03az",
                    "프로그래밍_언어론_ch03az",
                    "프로그래밍_언어론_ch03az",
                    "프로그래밍_언어론_ch03az",
                    "프로그래밍_언어론_ch03az",
                    "프로그래밍_언어론_ch03az"),
                onFileClick = {},
                loadMoreFile = {}
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun HomePreview() {
    MaterialTheme {
        HomeScreen()
    }
}