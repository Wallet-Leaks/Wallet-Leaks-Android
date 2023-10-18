package org.tbm.walletleaks.core.presentation.ui.components.typography.label

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography

@Composable
fun LabelSmall(
    modifier: Modifier = Modifier,
    color: Color = WalletLeaksTypography.Label.small.color,
    text: String
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = WalletLeaksTypography.Label.small,
        maxLines = 1
    )
}

@Preview
@Composable
private fun LabelSmallPreview() {
    Preview {
        LabelSmall(text = "OK")
    }
}