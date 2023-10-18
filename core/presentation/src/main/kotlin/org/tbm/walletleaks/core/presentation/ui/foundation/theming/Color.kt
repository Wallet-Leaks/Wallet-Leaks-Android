package org.tbm.walletleaks.core.presentation.ui.base.theming

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val primary = Color(0xFFFF7E0A)
val surface = Color(0xFF1B253E)
val surfaceContainer = Color(0xFF051330)
val surfaceContainerLowest = Color(0xFF222222)
val onSurfaceContainer = Color(0xFF5779D1)
val tertiary = Color(0xFFBBAABB)
val tertiaryContainer = Color(0xFF505B77)

data class WalletLeaksColorPalette(
    val primary: Color,
    val surface: Color,
    val surfaceContainer: Color,
    val surfaceContainerLowest: Color,
    val onSurfaceContainer: Color,
    val tertiary: Color,
    val tertiaryContainer: Color,
)

val palette = WalletLeaksColorPalette(
    primary = primary,
    surface = surface,
    surfaceContainer = surfaceContainer,
    surfaceContainerLowest = surfaceContainerLowest,
    onSurfaceContainer = onSurfaceContainer,
    tertiary = tertiary,
    tertiaryContainer = tertiaryContainer,
)

val LocalColorProvider =
    staticCompositionLocalOf<WalletLeaksColorPalette> { error("No default implementation") }

inline val colors: WalletLeaksColorPalette
    @Composable
    get() = LocalColorProvider.current

@Composable
fun WalletLeaksTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColorProvider provides palette,
        content = content
    )
}
