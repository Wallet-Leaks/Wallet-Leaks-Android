package org.tbm.walletleaks.core.presentation.ui.components.typography.label

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography

@Composable
fun LabelMedium(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    text: String
) {
    Text(
        modifier = modifier,
        style = WalletLeaksTypography.Label.medium,
        text = text,
        maxLines = 1,
        color = color
    )
}

@Preview
@Composable
private fun LabelMediumPreview() {
    Preview {
        LabelMedium(text = "Wallet Leaks")
    }
}