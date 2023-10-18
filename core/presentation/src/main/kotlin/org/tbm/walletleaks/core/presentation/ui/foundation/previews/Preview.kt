package org.tbm.walletleaks.core.presentation.ui.foundation.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.surface

@Composable
fun Preview(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .background(surface)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}