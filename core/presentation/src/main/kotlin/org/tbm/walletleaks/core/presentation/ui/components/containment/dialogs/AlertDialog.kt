@file:OptIn(ExperimentalMaterial3Api::class)

package org.tbm.walletleaks.core.presentation.ui.components.containment.dialogs

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import org.tbm.walletleaks.core.presentation.R
import org.tbm.walletleaks.core.presentation.ui.components.actions.ActionButton
import org.tbm.walletleaks.core.presentation.ui.components.actions.OutlinedActionButton
import org.tbm.walletleaks.core.presentation.ui.components.typography.body.BodySmall
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.surface

@Composable
fun AlertDialog(
    dialogProperties: DialogProperties = DialogProperties(),
    iconPainter: Painter,
    body: String,
    iconContentDescription: String? = null,
    positiveButtonText: String,
    negativeButtonText: String = "Cancel",
    positiveButtonClick: () -> Unit,
    negativeButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val constraints = ConstraintSet {
        val dialogIcon = createRefFor("siv")
        val bodyText = createRefFor("tv_body")
        val negativeBtn = createRefFor("btn_negative")
        val positiveBtn = createRefFor("btn_positive")

        constrain(dialogIcon) {
            centerHorizontallyTo(parent)
            top.linkTo(parent.top)
        }
        constrain(bodyText) {
            start.linkTo(parent.start)
            top.linkTo(dialogIcon.bottom)
            end.linkTo(parent.end)
        }
        constrain(negativeBtn) {
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
        }
        constrain(positiveBtn) {
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }
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
            ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .requiredWidth(36.dp)
                        .requiredHeight(36.dp)
                        .layoutId("siv"),
                    painter = iconPainter,
                    contentDescription = iconContentDescription
                )

                BodySmall(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            top = 8.dp,
                            end = 10.dp
                        )
                        .layoutId("tv_body"),
                    text = body
                )
                OutlinedActionButton(
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp)
                        .layoutId("btn_negative"),
                    text = negativeButtonText,
                    textStyle = WalletLeaksTypography.Label.small,
                    onClick = negativeButtonClick
                )
                ActionButton(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 10.dp)
                        .layoutId("btn_positive"),
                    text = positiveButtonText,
                    textStyle = WalletLeaksTypography.Label.small,
                    onClick = positiveButtonClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlertDialogPreview() {
    Preview {
        val context = LocalContext.current
        var openAlertDialog by remember {
            mutableStateOf(false)
        }
        ActionButton(text = "Open alert dialog", textStyle = WalletLeaksTypography.Label.large) {
            openAlertDialog = true
        }
        if (openAlertDialog) {
            AlertDialog(
                iconPainter = painterResource(id = R.drawable.ic_wallet_leaks),
                body =
                "Do you really want to log out of your account?",
                positiveButtonText = "Log out",
                negativeButtonClick = {
                    openAlertDialog = false
                },
                positiveButtonClick = {
                    Toast.makeText(context, "Logged out!", Toast.LENGTH_SHORT).show()
                    openAlertDialog = false
                }, onDismissRequest = {
                    openAlertDialog = false
                }
            )
        }
    }
}