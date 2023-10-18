package org.tbm.walletleaks.core.presentation.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.primary

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    text: String,
    onClick: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    androidx.compose.material.Button(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    if (textStyle == WalletLeaksTypography.Label.large) 14.dp else 8.dp
                )
            )
            .background(
                color = primary,
            )
            .requiredWidth(if (textStyle == WalletLeaksTypography.Label.large) 186.dp else 62.dp)
            .requiredHeight(if (textStyle == WalletLeaksTypography.Label.large) 36.dp else 28.dp),
        colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = primary),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            style = textStyle,
            text = text,
            textAlign = TextAlign.Center
        )
//        Box(
//            contentAlignment = Alignment.Center
//        ) {
//            if (isLoading.value) {
//                CircularProgressIndicator()
//            } else {
//                Text(
//                    modifier = Modifier.align(Alignment.Center),
//                    style = textStyle,
//                    text = text,
//                    textAlign = TextAlign.Center,
//                    maxLines = 1
//                )
//            }
//        }
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    val context = LocalContext.current
    Preview {
        ActionButton(text = "Log In", textStyle = WalletLeaksTypography.Label.small) {
            Toast.makeText(context, "Action Button", Toast.LENGTH_SHORT).show()
        }
    }
}