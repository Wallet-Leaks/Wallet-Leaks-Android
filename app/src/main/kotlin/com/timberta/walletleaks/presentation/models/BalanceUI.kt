package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.BalanceModel

data class BalanceUI(
    val id: Int,
    val balance: Double,
    val created: String,
    val coin: Int,
    val user: Int
)

fun BalanceModel.toUI() = BalanceUI(id, balance, created, coin, user)