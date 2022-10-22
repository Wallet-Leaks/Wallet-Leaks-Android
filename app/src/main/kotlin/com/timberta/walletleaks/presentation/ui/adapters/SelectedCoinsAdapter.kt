package com.timberta.walletleaks.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.ItemSelectedCoinBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.models.CoinUI

class SelectedCoinsAdapter :
    ListAdapter<CoinUI, SelectedCoinsAdapter.SelectedCoinsViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectedCoinsViewHolder(
        ItemSelectedCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SelectedCoinsViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class SelectedCoinsViewHolder(private val binding: ItemSelectedCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(selectedCoin: CoinUI) {
            binding.imCoin.apply {
                when (selectedCoin.symbol) {
                    "BTC" -> setImageResource(R.drawable.ic_btc)
                    "ETH" -> setImageResource(R.drawable.ic_eth)
                    "BNB" -> setImageResource(R.drawable.ic_bnb)
                    "LTC" -> setImageResource(R.drawable.ic_ltc)
                }
            }
        }
    }
}