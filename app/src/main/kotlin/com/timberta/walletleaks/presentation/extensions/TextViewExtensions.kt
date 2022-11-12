package com.timberta.walletleaks.presentation.extensions

import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import com.timberta.walletleaks.R

fun TextView.renderTextColorUsingTwoColors(
    vararg textColors: Int = intArrayOf(ContextCompat.getColor(context, R.color.safetyOrange)),
    firstText: String = "Wallet ",
    secondText: String = "Leaks"
) {
    text = SpannableStringBuilder(firstText).color(textColors[0]) {
        append(secondText)
    }
}