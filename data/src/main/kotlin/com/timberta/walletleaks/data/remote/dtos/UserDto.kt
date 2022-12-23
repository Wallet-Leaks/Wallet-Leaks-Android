package com.timberta.walletleaks.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.data.utils.DataMapper
import com.timberta.walletleaks.domain.models.UserModel
import java.math.BigDecimal

data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("coin_token")
    val cryptoWalletAddress: String?,
    @SerializedName("is_premium")
    val isPremium: Boolean,
    @SerializedName("date_joined")
    val dateJoined: String,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("balance")
    val balance: List<BalanceDto>,
    @SerializedName("total")
    val totalBalance: BigDecimal
) : DataMapper<UserModel> {
    override fun toDomain() =
        UserModel(
            id,
            username,
            photo.toString(),
            cryptoWalletAddress.toString(),
            isPremium,
            dateJoined,
            isVerified,
            balance.map { it.toDomain() },
            totalBalance
        )
}