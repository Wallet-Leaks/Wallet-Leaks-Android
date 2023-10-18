package org.tbm.walletleaks.core.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("coin")
    val coin: BalanceCoinDTO,
    @SerialName("user")
    val user: Int,
    @SerialName("balance")
    val balance: Double,
    @SerialName("created")
    val created: String
)