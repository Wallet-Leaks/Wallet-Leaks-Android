package org.tbm.walletleaks.core.presentation.ui.components.typography.title

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography

@Composable
fun TitleLarge(
    modifier: Modifier = Modifier,
    color: Color = WalletLeaksTypography.Title.large.color,
    text: String
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = WalletLeaksTypography.Title.large,
        maxLines = 1
    )
}

@Preview
@Composable
private fun TitleLargePreview() {
    Preview {
        TitleLarge(text = "Wallet Leaks")
    }
}