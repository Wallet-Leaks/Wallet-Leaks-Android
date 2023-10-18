package org.tbm.walletleaks.core.presentation.ui.components.actions

import androidx.annotation.DrawableRes
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.R
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksRippleTheme

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    iconButtonModifier: Modifier = Modifier,
    @DrawableRes painterResource: Int = R.drawable.ic_tick,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides WalletLeaksRippleTheme()) {
        IconButton(
            onClick = {
                onClick()
            },
            modifier = iconButtonModifier
        ) {
            Icon(
                painter = painterResource(id = painterResource),
                contentDescription = contentDescription,
                tint = tint,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun IconButtonPreview() {
    Preview {
        IconButton {

        }
    }
}