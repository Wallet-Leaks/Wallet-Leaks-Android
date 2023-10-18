package org.tbm.walletleaks.core.data.dtos

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class UserDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("photo")
    val photo: String?,
    @SerialName("coin_token")
    val cryptoWalletAddress: String?,
    @SerialName("is_premium")
    val isPremium: Boolean,
    @SerialName("date_joined")
    val dateJoined: String,
    @SerialName("is_verified")
    val isVerified: Boolean,
    @SerialName("balance")
    val balance: List<BalanceDTO>,
    @SerialName("total")
    @Contextual
    val totalBalance: BigDecimal
)