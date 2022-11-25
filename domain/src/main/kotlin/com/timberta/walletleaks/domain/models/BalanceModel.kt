package com.timberta.walletleaks.domain.models

data class BalanceModel(
    val id: Int,
    val balance: Double,
    val created: String,
    val coin: BalanceCoinModel,
    val user: Int
)