package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.BalanceCoinModel

data class BalanceCoinUI(
    val id: Int,
    val symbol: String,
    val price: String
)

fun BalanceCoinModel.toUI() = BalanceCoinUI(id, symbol, price)