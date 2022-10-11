package com.timberta.walletleaks.presentation.base

import androidx.recyclerview.widget.DiffUtil

interface BaseDiffModel<T> {
    val address: T?
    override fun equals(other: Any?): Boolean
}

class BaseDiffUtil<T : BaseDiffModel<S>, S> :
    DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}