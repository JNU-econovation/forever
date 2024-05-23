package com.fourever.forever.presentation.fileupload

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.presentation.SCREEN_MARGIN
import com.fourever.forever.presentation.component.buttons.FileUploadBtn
import com.fourever.forever.presentation.component.buttons.LongColorBtn
import com.fourever.forever.presentation.component.topappbar.FileUploadTopAppBar
import com.fourever.forever.ui.theme.foreverTypography

private const val SPACE_BETWEEN_TITLE_AND_BTN = 130
private const val SPACE_BETWEEN_BTNS = 30

@Composable
fun FileUploadScreen(
    fileUploadUiState: FileUploadUiState,
    updateFileChosenState: (Boolean) -> Unit,
    updateFileName: (String) -> Unit,
    updateFileUri: (Uri) -> Unit,
    navigateToGenerateSummary: () -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectedUri ->
        if (selectedUri != null) {
            val contentResolver = context.contentResolver
            val cursor = contentResolver.query(selectedUri, null, null, null, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (displayNameIndex != -1) {
                        val fileName = it.getString(displayNameIndex)

                        updateFileChosenState(true)
                        updateFileName(fileName)
                        updateFileUri(selectedUri)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = { FileUploadTopAppBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(SCREEN_MARGIN.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.file_upload_title),
                    style = foreverTypography.titleMedium,
                    color = colorResource(id = R.color.paragraph)
                )
                Text(
                    text = stringResource(id = R.string.file_upload_subtitle),
                    style = foreverTypography.bodySmall,
                    color = colorResource(id = R.color.subtitle)
                )
            }
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_BTN.dp))
            FileUploadBtn(
                isFileChosen = fileUploadUiState.isFileChosen,
                fileName = fileUploadUiState.fileName,
                onClick = { launcher.launch("*/*") }
            )
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_BTNS.dp))
            LongColorBtn(
                text = stringResource(id = R.string.file_upload_summary_button),
                enabled = fileUploadUiState.isFileChosen,
                onClick = navigateToGenerateSummary
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun FileUploadPreview() {
    MaterialTheme {
        FileUploadScreen(
            fileUploadUiState = FileUploadUiState(),
            updateFileChosenState = {},
            updateFileName = {},
            updateFileUri = {},
            navigateToGenerateSummary = {}
        )
    }
}