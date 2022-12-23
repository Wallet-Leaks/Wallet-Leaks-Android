package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.domain.models.GeneralUserInfoModel

data class GeneralUserInfoDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("photo")
    val photo: String? = null,
    @SerializedName("coin_token")
    val cryptoWalletAddress: String?,
    @SerializedName("is_premium")
    val isPremium: Boolean? = null,
    @SerializedName("date_joined")
    val dateJoined: String? = null,
    @SerializedName("is_verified")
    val isVerified: Boolean? = null,
    @SerializedName("balance")
    val balance: List<ModifyUserBalanceDto>? = null,
    @SerializedName("total")
    val totalBalance: Double? = null
)

fun GeneralUserInfoModel.toData() = GeneralUserInfoDto(
    id,
    username,
    photo,
    cryptoWalletAddress,
    isPremium,
    dateJoined,
    isVerified,
    balance?.map { it.toData() },
    totalBalance
)