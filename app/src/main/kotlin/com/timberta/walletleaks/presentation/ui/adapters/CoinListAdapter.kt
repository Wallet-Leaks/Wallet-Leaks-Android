package com.timberta.walletleaks.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.timberta.walletleaks.databinding.ItemCoinBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.extensions.loadImageWithGlide
import com.timberta.walletleaks.presentation.models.CoinUI


class CoinListAdapter(
    private val doesUserHavePremium: Boolean,
    private val onItemClick: ((coinsCount: Int, hasUserTriedToSelectMultipleCoins: Boolean) -> Unit)? = null
) :
    PagingDataAdapter<CoinUI, CoinListAdapter.CoinsViewHolder>(BaseDiffUtil()) {
    private val selectedCoins = arrayListOf<CoinUI>()
    private var isUserSelectingCoins = false
    private var hasUserTriedToSelectMultipleCoins = false
    private var lastlySelectedPosition = RecyclerView.NO_POSITION

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
                    imIsCoinSelected.isVisible =
                        lastlySelectedPosition == absoluteAdapterPosition

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
                        notifyItemChanged(absoluteAdapterPosition)
                        when (!selectedCoins.contains(coin) && selectedCoins.count() < 4) {
                            true -> {
                                selectedCoins.add(coin)
                                lastlySelectedPosition = absoluteAdapterPosition
                                notifyItemChanged(lastlySelectedPosition)
                            }
                            false -> {
                                selectedCoins.remove(coin)
                                lastlySelectedPosition = RecyclerView.NO_POSITION
                                notifyItemChanged(lastlySelectedPosition)
                            }
                        }
                    }
                }
            }
        }
    }
}