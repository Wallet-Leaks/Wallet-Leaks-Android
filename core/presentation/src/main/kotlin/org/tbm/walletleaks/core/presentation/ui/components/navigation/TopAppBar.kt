@file:OptIn(ExperimentalMaterial3Api::class)

package org.tbm.walletleaks.core.presentation.ui.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.tbm.walletleaks.core.presentation.R
import org.tbm.walletleaks.core.presentation.ui.components.containment.dividers.Underline
import org.tbm.walletleaks.core.presentation.ui.components.actions.IconButton
import org.tbm.walletleaks.core.presentation.ui.components.typorgraphy.title.TitleLarge
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.surfaceContainer

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    shouldIncludeBackButton: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    backButtonClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Column {
        TopAppBar(
            title = {
                TitleLarge(
                    text = title,
                )
            },
            modifier = modifier,
            navigationIcon = {
                if (shouldIncludeBackButton) {
                    IconButton(
                        painterResource = R.drawable.ic_back,
                    ) {
                        backButtonClick()
                    }
                }
            },
            actions = {
                actions()
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = surfaceContainer),
            scrollBehavior = scrollBehavior,
        )
        Underline()
    }
}

@Preview
@Composable
private fun ToolbarPreview() {
    Preview {
        Toolbar(
            title = "Wallet Leaks",
            shouldIncludeBackButton = false,
        )
    }
}

@Preview
@Composable
private fun ToolbarPreviewWithBackButton() {
    Preview {
        Toolbar(
            title = "Wallet Leaks",
            shouldIncludeBackButton = true,
        )
    }
}