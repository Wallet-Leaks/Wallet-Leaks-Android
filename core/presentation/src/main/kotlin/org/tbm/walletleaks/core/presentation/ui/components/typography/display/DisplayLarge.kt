package org.tbm.walletleaks.core.presentation.ui.components.typography.display

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography

@Composable
fun DisplayLarge(
    modifier: Modifier = Modifier,
    color: Color = WalletLeaksTypography.Display.large.color,
    text: String
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = WalletLeaksTypography.Display.large,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}

@Preview
@Composable
private fun DisplayLargePreview() {
    Preview {
        DisplayLarge(text = "Wallet Leaks")
    }
}