package org.tbm.walletleaks.core.presentation.ui.components.communication.progressIndicators

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.tbm.walletleaks.core.presentation.R
import org.tbm.walletleaks.core.presentation.ui.foundation.previews.Preview

@Composable
fun CircularProgressIndicator(modifier: Modifier = Modifier) {
    val rotationAnim = rememberInfiniteTransition(label = "")
    val rotationValue by rotationAnim.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = modifier
            .size(30.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_progressbar),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationZ = rotationValue
                }
        )
    }
}

@Preview
@Composable
private fun CircularProgressIndicatorPreview() {
    Preview {
        CircularProgressIndicator()
    }
}