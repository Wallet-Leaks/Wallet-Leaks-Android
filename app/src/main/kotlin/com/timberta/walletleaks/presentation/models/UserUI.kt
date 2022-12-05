package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.UserModel

data class UserUI(
    val id: Int,
    val username: String,
    val photo: String?,
    val isPremium: Boolean,
    val dateJoined: String,
    val isVerified: Boolean,
    val balance: List<BalanceUI>,
    val totalBalance: Double
)

fun UserModel.toUI() =
    UserUI(id, username, photo, isPremium, dateJoined, isVerified, balance.map { it.toUI() }, totalBalance)