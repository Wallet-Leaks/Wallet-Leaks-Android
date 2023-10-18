package org.tbm.walletleaks.core.presentation.ui.components.containment.dividers

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.tertiaryContainer

@Composable
fun Underline(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = tertiaryContainer
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}

@Preview
@Composable
private fun UnderlinePreview() {
    Preview {
        Underline()
    }
}