package org.tbm.walletleaks.core.presentation.ui.components.typography.body

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography

@Composable
fun BodySmall(
    modifier: Modifier = Modifier,
    color: Color = WalletLeaksTypography.Body.small.color,
    text: String
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = WalletLeaksTypography.Body.small,
    )
}

@Preview
@Composable
private fun BodySmallPreview() {
    Preview {
        BodySmall(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
    }
}