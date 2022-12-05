package com.timberta.walletleaks.domain.models

data class ModifyUserBalanceModel(
    val coin: Int,
    val balance: Double
)