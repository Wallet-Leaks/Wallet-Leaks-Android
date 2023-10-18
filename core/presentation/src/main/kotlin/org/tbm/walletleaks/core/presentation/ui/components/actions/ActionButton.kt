package org.tbm.walletleaks.core.presentation.ui.components.actions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tbm.walletleaks.core.presentation.ui.components.communication.progressIndicators.CircularProgressIndicator
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.primary
import kotlin.time.Duration.Companion.seconds

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    text: String,
    shouldDisplayProgressBar: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    if (textStyle == WalletLeaksTypography.Label.large) 14.dp else 8.dp
                )
            )
            .background(
                color = primary,
            )
            .requiredWidth(if (textStyle == WalletLeaksTypography.Label.large) 186.dp else 78.dp)
            .requiredHeight(if (textStyle == WalletLeaksTypography.Label.large) 36.dp else 28.dp),
        enabled = !shouldDisplayProgressBar,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = primary,
            disabledBackgroundColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    ) {
        AnimatedVisibility(
            visible = shouldDisplayProgressBar,
        ) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(
            visible = !shouldDisplayProgressBar
        ) {
            Text(
                text = text,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember {
        mutableStateOf(false)
    }
    Preview {
        ActionButton(
            text = "Log In",
            textStyle = WalletLeaksTypography.Label.large,
            shouldDisplayProgressBar = isLoading
        ) {
            isLoading = !isLoading
            coroutineScope.launch {
                delay(2.5.seconds)
                isLoading = !isLoading
            }
        }
    }
}