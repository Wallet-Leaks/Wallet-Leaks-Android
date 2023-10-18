package org.tbm.walletleaks.core.presentation.ui.foundation.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class WalletLeaksRippleTheme(private val color: Color = Color.Gray) : RippleTheme {

    @Composable
    override fun defaultColor() = color

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        color,
        lightTheme = isSystemInDarkTheme()
    )
}