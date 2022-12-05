package com.timberta.walletleaks.domain.models

data class GeneralUserInfoModel(
    val id: Int? = null,
    val username: String? = null,
    val photo: String? = null,
    val isPremium: Boolean? = null,
    val dateJoined: String? = null,
    val isVerified: Boolean? = null,
    val balance: List<BalanceModel>? = null,
    val totalBalance: Double? = null
)