package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.ModifyUserBalanceModel

data class ModifyUserBalanceUI(
    val coin: Int,
    val balance: Double
)

fun ModifyUserBalanceUI.toDomain() = ModifyUserBalanceModel(coin, balance)