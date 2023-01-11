package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.BalanceModel

data class BalanceUI(
    val id: Int,
    val balance: Double,
    val created: String,
    val coin: BalanceCoinUI,
    val user: Int
)

fun BalanceModel.toUI() = BalanceUI(id, balance, created, coin.toUI(), user)
fun BalanceUI.toDomain() = BalanceModel(id, balance, created, coin.toDomain(), user)