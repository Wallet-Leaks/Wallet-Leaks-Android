package com.timberta.walletleaks.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.CryptoItemBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.ui.model.CryptoWalletUI

class CryptoAlgorithmAdapter :
    ListAdapter<CryptoWalletUI, CryptoAlgorithmAdapter.ViewHolder>(BaseDiffUtil()) {

    private var lastPosition = -1

    inner class ViewHolder(private val binding: CryptoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(walletUI: CryptoWalletUI) = with(binding) {
            tvAddressCoin.text = walletUI.address
            tvPrivateKey.text = walletUI.privateKey
            tvBalanceCoin.text = String.format("%.4f", walletUI.price)
            if (walletUI.price > 0) {
                tvAddressCoin.setTextColor(Color.parseColor("#d9393d"))
                tvPrivateKey.setTextColor(Color.parseColor("#d9393d"))
                tvBalanceCoin.setTextColor(Color.parseColor("#d9393d"))
                tvStateAddress.setTextColor(Color.parseColor("#d9393d"))
                tvStatePrivateKey.setTextColor(Color.parseColor("#d9393d"))
                tvStateBalance.setTextColor(Color.parseColor("#d9393d"))
            } else {
                tvAddressCoin.setTextColor(Color.parseColor("#BBAABB"))
                tvPrivateKey.setTextColor(Color.parseColor("#BBAABB"))
                tvBalanceCoin.setTextColor(Color.parseColor("#BBAABB"))
                tvStateAddress.setTextColor(Color.parseColor("#BBAABB"))
                tvStatePrivateKey.setTextColor(Color.parseColor("#BBAABB"))
                tvStateBalance.setTextColor(Color.parseColor("#BBAABB"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CryptoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.item_anim)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}
