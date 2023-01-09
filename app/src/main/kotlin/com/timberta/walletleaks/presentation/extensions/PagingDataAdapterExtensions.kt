package com.timberta.walletleaks.presentation.extensions

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout

fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.bindViewsToPagingLoadStates(
    recyclerView: RecyclerView,
    progressBar: ProgressBar? = null,
    vararg viewsToBindToLoadStateNotLoading: View = emptyArray(),
    shimmerFrameLayout: ShimmerFrameLayout? = null,
    smartRefreshLayout: SmartRefreshLayout? = null
) {
    addLoadStateListener { loadState ->
        recyclerView.isVisible = loadState.refresh is LoadState.NotLoading
        progressBar?.isVisible = loadState.refresh is LoadState.Loading
        when (loadState.refresh is LoadState.Loading) {
            true -> {
                shimmerFrameLayout?.visible()
                shimmerFrameLayout?.showShimmer(true)
                smartRefreshLayout?.isEnabled = false
            }
            false -> {
                shimmerFrameLayout?.stopShimmer()
                shimmerFrameLayout?.hideShimmer()
                shimmerFrameLayout?.gone()
                smartRefreshLayout?.isEnabled = true
            }
        }
        viewsToBindToLoadStateNotLoading.forEach {
            it.isVisible = loadState.refresh is LoadState.NotLoading
        }
    }
}