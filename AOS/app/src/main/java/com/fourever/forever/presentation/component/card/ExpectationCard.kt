package com.fourever.forever.presentation.component.card

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.R
import com.fourever.forever.ui.theme.foreverTypography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ExpectationCard() {
    var expectation by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current


    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.paragraph)
        ),
        modifier = Modifier
            .size(width = CARD_WIDTH.dp, height = CARD_HEIGHT.dp)
            .border(
                width = CARD_STROKE_THICKNESS.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.purple_medium),
                        colorResource(id = R.color.blue_medium),
                    )
                ),
                shape = RoundedCornerShape(CARD_RADIUS.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = CARD_HORIZONTAL_PADDING.dp,
                    vertical = CARD_VERTICAL_PADDING.dp
                ),
        ) {
            Text(
                text = stringResource(id = R.string.expectation),
                style = foreverTypography.titleSmall
            )
            Spacer(modifier = Modifier.size(SPACE_BETWEEN_TITLE_AND_CONTENT.dp))
            BasicTextField(
                value = expectation,
                onValueChange = { expectation = it },
                modifier = Modifier.size(
                    width = CARD_CONTENT_WIDTH.dp,
                    height = CARD_CONTENT_HEIGHT.dp
                ),
                visualTransformation = VisualTransformation.None,
                singleLine = false,
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            ) {
                TextFieldDefaults.DecorationBox(
                    value = expectation,
                    innerTextField = it,
                    enabled = true,
                    singleLine = false,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    isError = expectation.length > CARD_CONTENT_MAX_LENGTH,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.expectation_placeholder),
                            style = foreverTypography.bodySmall,
                            color = colorResource(id = R.color.placeholder)
                        )
                    },
                    contentPadding = PaddingValues(CARD_CONTENT_PADDING.dp),
                    container = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardPreview() {
    MaterialTheme {
        ExpectationCard()
    }
}