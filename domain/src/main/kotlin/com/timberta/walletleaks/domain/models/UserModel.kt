package com.timberta.walletleaks.domain.models

import java.math.BigDecimal

data class UserModel(
    val id: Int,
    val username: String,
    val photo: String?,
    val isPremium: Boolean,
    val dateJoined: String,
    val isVerified: Boolean,
    val balance: List<BalanceModel>,
    val totalBalance: BigDecimal
)