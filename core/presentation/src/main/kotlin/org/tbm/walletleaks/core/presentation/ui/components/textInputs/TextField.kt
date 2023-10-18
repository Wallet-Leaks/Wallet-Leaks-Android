package org.tbm.walletleaks.core.presentation.ui.components.textInputs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.tbm.walletleaks.core.presentation.R
import org.tbm.walletleaks.core.presentation.ui.components.actions.IconButton
import org.tbm.walletleaks.core.presentation.ui.components.containment.dividers.Underline
import org.tbm.walletleaks.core.presentation.ui.components.typorgraphy.label.LabelMedium
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.tertiaryContainer
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.walletLeaksTextSelectionColors

@Composable
fun TextField(
    hint: String,
    modifier: Modifier = Modifier,
    underlineModifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = false,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
) {
    var text by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {
        CompositionLocalProvider(LocalTextSelectionColors provides walletLeaksTextSelectionColors) {
            BasicTextField(
                value = text,
                onValueChange = { textValue ->
                    text = textValue
                },
                textStyle = WalletLeaksTypography.Label.medium,
                modifier = modifier
                    .padding(start = 4.dp),
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp),
                            contentAlignment = Alignment.BottomStart,
                        ) {
                            if (text.isEmpty()) {
                                LabelMedium(
                                    color = tertiaryContainer,
                                    text = hint
                                )
                            }
                            innerTextField()
                        }
                        Box(
                            modifier = Modifier.padding(end = 4.dp, bottom = 4.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            trailingIcon()
                        }

                    }
                }
            )
        }
        Underline(
            modifier = underlineModifier
        )
    }
}

@Preview
@Composable
private fun TextFieldPreview() {
    var togglePassword by rememberSaveable {
        mutableStateOf(false)
    }
    Preview {
        TextField(
            hint = "Name",
            visualTransformation = if (togglePassword) {
                PasswordVisualTransformation('*')
            } else {
                VisualTransformation.None
            }, trailingIcon = {
                IconButton(
                    iconButtonModifier = Modifier
                        .size(23.dp),
                    painterResource = if (togglePassword) R.drawable.ic_back else R.drawable.ic_tick
                ) {
                    togglePassword = !togglePassword
                }
            }
        )
    }
}