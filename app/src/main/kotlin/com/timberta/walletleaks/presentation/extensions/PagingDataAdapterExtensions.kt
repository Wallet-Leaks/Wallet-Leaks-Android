package com.timberta.walletleaks.presentation.extensions

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout

fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.bindViewsToPagingLoadStates(
    recyclerView: RecyclerView,
    progressBar: ProgressBar? = null,
    vararg viewsToBindToLoadStateNotLoading: View = emptyArray(),
    shimmerFrameLayout: ShimmerFrameLayout? = null,
    refreshLayout: SwipeRefreshLayout? = null
) {
    addLoadStateListener { loadState ->
        recyclerView.isVisible = loadState.refresh is LoadState.NotLoading
        progressBar?.isVisible = loadState.refresh is LoadState.Loading
        refreshLayout?.isEnabled = shimmerFrameLayout?.isVisible == false
        when (loadState.refresh is LoadState.Loading) {
            true -> {
                shimmerFrameLayout?.visible()
                shimmerFrameLayout?.showShimmer(false)
                shimmerFrameLayout?.startShimmer()
            }
            false -> {
                shimmerFrameLayout?.stopShimmer()
                shimmerFrameLayout?.hideShimmer()
                shimmerFrameLayout?.gone()
            }
        }
        viewsToBindToLoadStateNotLoading.forEach {
            it.isVisible = loadState.refresh is LoadState.NotLoading
        }
    }
}