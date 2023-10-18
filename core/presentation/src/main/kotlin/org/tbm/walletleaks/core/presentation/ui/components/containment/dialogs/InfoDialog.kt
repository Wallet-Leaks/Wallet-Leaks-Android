@file:OptIn(ExperimentalMaterial3Api::class)

package org.tbm.walletleaks.core.presentation.ui.components.containment.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.tbm.walletleaks.core.presentation.R
import org.tbm.walletleaks.core.presentation.ui.components.actions.ActionButton
import org.tbm.walletleaks.core.presentation.ui.components.typography.body.BodySmall
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.surface

@Composable
fun InfoDialog(
    dialogProperties: DialogProperties = DialogProperties(),
    iconPainter: Painter,
    text: String,
    iconContentDescription: String? = null,
    buttonText: String = "OK",
    onDismissRequest: () -> Unit,
    buttonClick: () -> Unit = { onDismissRequest() },
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = dialogProperties
    ) {
        Card(
            modifier = Modifier
                .requiredWidth(196.dp)
                .requiredHeight(140.dp)
                .background(surface, shape = RoundedCornerShape(7.dp)),
            colors = CardDefaults.cardColors(containerColor = surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .requiredWidth(36.dp)
                        .requiredHeight(36.dp),
                    painter = iconPainter,
                    contentDescription = iconContentDescription
                )

                BodySmall(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        top = 8.dp,
                        end = 10.dp
                    ),
                    text = text
                )

                ActionButton(
                    modifier = Modifier
                        .padding(end = 8.dp, top = 20.dp, bottom = 8.dp)
                        .align(Alignment.End),
                    text = buttonText,
                    textStyle = WalletLeaksTypography.Label.small,
                    onClick = buttonClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun InfoDialogPreview() {
    Preview {
        var openAlertDialog by remember {
            mutableStateOf(false)
        }
        ActionButton(text = "Open info dialog", textStyle = WalletLeaksTypography.Label.large) {
            openAlertDialog = true
        }
        if (openAlertDialog) {
            InfoDialog(
                text = "To start, select coins & mining time by tapping on filter.",
                iconPainter = painterResource(id = R.drawable.ic_wallet_leaks),
                onDismissRequest = {
                    openAlertDialog = false
                }
            )
        }
    }
}