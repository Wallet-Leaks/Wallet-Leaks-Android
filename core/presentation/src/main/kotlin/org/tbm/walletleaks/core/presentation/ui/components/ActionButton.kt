package org.tbm.walletleaks.core.presentation.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.tbm.walletleaks.core.presentation.ui.base.colors

@Preview(showBackground = false)
@Composable
fun ActionButton(text: String = "", modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .wrapContentWidth()
            .height(34.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary
        ),
        onClick = {
            onClick()
        }) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 0.9.sp
        )
    }
}