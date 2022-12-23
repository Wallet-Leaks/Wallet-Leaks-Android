package com.timberta.walletleaks.domain.models

data class UserModel(
    val id: Int,
    val username: String,
    val photo: String,
    val cryptoWalletAddress :String,
    val isPremium: Boolean,
    val dateJoined: String,
    val isVerified: Boolean,
    val balance: List<BalanceModel>,
    val totalBalance: Double
)