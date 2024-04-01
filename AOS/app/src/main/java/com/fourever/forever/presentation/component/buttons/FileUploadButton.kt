package com.fourever.forever.presentation.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R

private const val FILE_UPLOAD_BTN_WIDTH = 320
private const val FILE_UPLOAD_BTN_HEIGHT = 212
private const val FILE_UPLOAD_BTN_RADIUS = 5
private const val FILE_UPLOAD_BTN_STROKE_THICKNESS = 1.5
private const val SPACE_BETWEEN_ICON_AND_PARAGRAPH = 15

@Composable
fun FileUploadBtn(isFileChosen: Boolean, fileName: String = "") {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(width = FILE_UPLOAD_BTN_WIDTH.dp, height = FILE_UPLOAD_BTN_HEIGHT.dp),
        shape = RoundedCornerShape(FILE_UPLOAD_BTN_RADIUS.dp),
        border = BorderStroke(
            width = FILE_UPLOAD_BTN_STROKE_THICKNESS.dp,
            color = if (isFileChosen) {
                colorResource(id = R.color.secondary_strong)
            } else {
                colorResource(id = R.color.secondary_medium)
            }
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.secondary_light),
            contentColor = if (isFileChosen) {
                colorResource(id = R.color.secondary_strong)
            } else {
                colorResource(id = R.color.secondary_medium)
            }
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isFileChosen) {
                Image(
                    painter = painterResource(id = R.drawable.ic_upload_file_chosen),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_ICON_AND_PARAGRAPH.dp))
                Text(
                    text = fileName,
                    style = MaterialTheme.typography.labelSmall,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_upload_add_file),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_ICON_AND_PARAGRAPH.dp))
                Text(
                    text = stringResource(id = R.string.file_upload_notice),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BtnPreview() {
    MaterialTheme {
        FileUploadBtn(true, "프로그래밍_언어론_ch03a")
    }
}