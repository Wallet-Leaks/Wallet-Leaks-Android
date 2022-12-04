package com.timberta.walletleaks.presentation.extensions

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout
import com.timberta.walletleaks.presentation.ui.state.UIState

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun makeMultipleViewsInvisible(vararg views: View) {
    views.forEach {
        it.invisible()
    }
}

fun <T> ProgressBar.bindToUIStateLoading(uiState: UIState<T>) {
    isVisible = uiState is UIState.Loading
}

fun <T> ShimmerFrameLayout.bindToUIStateLoading(uiState: UIState<T>) {
    when (uiState) {
        is UIState.Loading -> startShimmer()
        else -> {
            stopShimmer()
            hideShimmer()
        }
    }
}