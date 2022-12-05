package com.timberta.walletleaks.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.timberta.walletleaks.databinding.ItemCryptocurrencyToWithdrawBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.models.CoinUI

class CryptocurrencyToWithdrawAdapter(private val onItemClick: (id: Int, symbol: String, price: String) -> Unit) :
    ListAdapter<CoinUI, CryptocurrencyToWithdrawAdapter.CryptocurrencyToWithdrawViewHolder>(
        BaseDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CryptocurrencyToWithdrawViewHolder(
        ItemCryptocurrencyToWithdrawBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CryptocurrencyToWithdrawViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class CryptocurrencyToWithdrawViewHolder(private val binding: ItemCryptocurrencyToWithdrawBinding) :
        ViewHolder(binding.root) {
        fun onBind(coin: CoinUI) {
            binding.tvCryptocurrencySymbol.text = coin.symbol
        }

        init {
            binding.root.setOnClickListener {
                onItemClick(
                    getItem(absoluteAdapterPosition).id,
                    getItem(absoluteAdapterPosition).symbol.toString(),
                    getItem(absoluteAdapterPosition).price?.replace(",", "")?.substringAfter("$")
                        .toString()
                )
            }
        }
    }
}