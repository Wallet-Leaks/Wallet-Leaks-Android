package com.timberta.walletleaks.presentation.ui.adapters

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.timberta.walletleaks.databinding.ItemCoinBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.extensions.invisible
import com.timberta.walletleaks.presentation.extensions.loadImageWithGlide
import com.timberta.walletleaks.presentation.extensions.visible
import com.timberta.walletleaks.presentation.models.CoinUI
import jp.wasabeef.blurry.Blurry


class CoinListAdapter(
    private val doesUserHavePremium: Boolean,
    private val onItemClick: ((coinsCount: Int, hasUserTriedToSelectMultipleCoins: Boolean) -> Unit)? = null
) :
    PagingDataAdapter<CoinUI, CoinListAdapter.CoinsViewHolder>(BaseDiffUtil()) {
    private val selectedCoins = arrayListOf<CoinUI>()
    private var isUserSelectingCoins = false
    private var hasUserTriedToSelectMultipleCoins = false
    private var lastlySelectedPosition = RecyclerView.NO_POSITION
    private var firstSelectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CoinsViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: CoinsViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.onBind(it) }
    }

    fun startSelectingCoins() {
        isUserSelectingCoins = true
    }

    fun getSelectedCoins() = selectedCoins.toTypedArray()

    inner class CoinsViewHolder(private val binding: ItemCoinBinding) : ViewHolder(binding.root) {
        fun onBind(item: CoinUI) {
            item.apply {
                binding.apply {
                    isSelected =
                        when (lastlySelectedPosition == absoluteAdapterPosition || firstSelectedPosition == absoluteAdapterPosition) {
                            true -> true
                            false -> false
                        }
                    if (!isAvailable) {
                        val handler = Looper.myLooper()?.let { Handler(it) }
                        handler?.postDelayed({
                            Blurry.with(root.context).radius(5)
                                .color(Color.parseColor("#663D3D3D"))
                                .onto(root)
                        }, 0.1.toLong())
                    }
                    when (isSelected) {
                        true -> imIsCoinSelected.visible()
                        false -> imIsCoinSelected.invisible()
                    }
                    imCoin.loadImageWithGlide(icon)
                    tvCoinSymbol.text = symbol
                    tvCoinName.text = title
                    if (!isUserSelectingCoins)
                        tvCoinBalance.text = price
                    if (isUserSelectingCoins && isAvailable)
                        root.setOnClickListener {
                            handleCoinSelection()
                            onItemClick?.invoke(
                                selectedCoins.count(),
                                hasUserTriedToSelectMultipleCoins
                            )
                        }
                }
            }
        }

        private fun handleCoinSelection() {
            getItem(absoluteAdapterPosition)?.let { coin ->
                when (doesUserHavePremium) {
                    false -> {
                        if (lastlySelectedPosition >= 0)
                            notifyItemChanged(lastlySelectedPosition)
                        when (!selectedCoins.contains(
                            coin
                        ) && selectedCoins.isEmpty()) {
                            true -> {
                                selectedCoins.add(
                                    coin
                                )
                                lastlySelectedPosition = absoluteAdapterPosition
                                notifyItemChanged(lastlySelectedPosition)
                            }
                            false -> {
                                selectedCoins.remove(coin)
                                lastlySelectedPosition = RecyclerView.NO_POSITION
                                notifyItemChanged(lastlySelectedPosition)
                            }
                        }
                        if (selectedCoins.count() == 1) {
                            hasUserTriedToSelectMultipleCoins = selectedCoins[0] != coin
                            selectedCoins[0] = coin
                            lastlySelectedPosition = absoluteAdapterPosition
                            notifyItemChanged(lastlySelectedPosition)
                        }
                    }
                    true -> {
//                        if (lastlySelectedPosition >= 0 && firstSelectedPosition != absoluteAdapterPosition) {
//                            notifyItemChanged(lastlySelectedPosition)
//                        }
                        lastlySelectedPosition = absoluteAdapterPosition
                        notifyItemChanged(lastlySelectedPosition)

                        when (!selectedCoins.contains(
                            coin
                        ) && selectedCoins.count() < 3) {
                            true -> {
                                if (firstSelectedPosition == RecyclerView.NO_POSITION) {
                                    firstSelectedPosition = absoluteAdapterPosition
                                }
                                selectedCoins.add(
                                    coin
                                )
                            }
                            false -> {
                                selectedCoins.remove(coin)
                                if (firstSelectedPosition == absoluteAdapterPosition) {
                                    firstSelectedPosition = RecyclerView.NO_POSITION
                                    notifyItemChanged(firstSelectedPosition)
                                }
                                lastlySelectedPosition = RecyclerView.NO_POSITION
                                notifyItemChanged(lastlySelectedPosition)
                            }
                        }
                        if (selectedCoins.count() == 3 && !selectedCoins.contains(
                                coin
                            )
                        ) {
                            selectedCoins[0] =
                                coin
//                            firstSelectedPosition = absoluteAdapterPosition
                            lastlySelectedPosition = absoluteAdapterPosition
                            notifyItemChanged(lastlySelectedPosition)
//                            Log.e("gaypopNewlySelectedItem", firstSelectedPosition.toString())
//                            notifyItemChanged(firstSelectedPosition)
                        }
                    }
                }
            }
        }
    }
}