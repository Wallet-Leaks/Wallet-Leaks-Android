package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.GeneralUserInfoModel

data class GeneralUserInfoUI(
    val id: Int? = null,
    val username: String? = null,
    val photo: String? = null,
    val isPremium: Boolean? = null,
    val dateJoined: String? = null,
    val isVerified: Boolean? = null,
    val balance: List<ModifyUserBalanceUI>? = null,
    val totalBalance: Double? = null
)

fun GeneralUserInfoUI.toDomain() = GeneralUserInfoModel(
    id,
    username,
    photo,
    isPremium,
    dateJoined,
    isVerified,
    balance?.map { it.toDomain() },
    totalBalance
)