package org.tbm.walletleaks.core.presentation.ui.components.actions

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTypography

@Composable
fun OutlinedActionButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .wrapContentWidth(unbounded = true)
            .requiredHeightIn(max = 28.dp),
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
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
    }
}

@Preview
@Composable
private fun OutlinedActionButtonPreview() {
    Preview {
        val context = LocalContext.current
        OutlinedActionButton(textStyle = WalletLeaksTypography.Label.small, text = "Log out") {
            Toast.makeText(context, "Logged out!", Toast.LENGTH_SHORT).show()
        }
    }
}