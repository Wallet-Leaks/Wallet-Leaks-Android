package com.timberta.walletleaks.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.ItemUserActionsBinding
import com.timberta.walletleaks.presentation.base.BaseDiffUtil
import com.timberta.walletleaks.presentation.models.UserActionsInfoUI

class UserActionsInfoAdapter(private val onItemClick: ((actionName: String) -> Unit)? = null) :
    ListAdapter<UserActionsInfoUI, UserActionsInfoAdapter.UserActionsViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserActionsViewHolder(
        ItemUserActionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UserActionsViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class UserActionsViewHolder(private val binding: ItemUserActionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(userActionsInfoUI: UserActionsInfoUI) {
            binding.imUserAction.setImageResource(userActionsInfoUI.actionImage)
            binding.tvTextUserAction.text = userActionsInfoUI.actionName
        }

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    onItemClick?.invoke(it.actionName)
                }
            }
        }
    }
    init {
        submitList(
            listOf(
                UserActionsInfoUI("Withdrawal", R.drawable.ic_wallet_withdrawal, 1),
                UserActionsInfoUI("Premium+", R.drawable.ic_premium, 3),
                UserActionsInfoUI("Settings", R.drawable.ic_settings, 4),
                UserActionsInfoUI("Exit", R.drawable.ic_exit, 5)
            )
        )
    }
}