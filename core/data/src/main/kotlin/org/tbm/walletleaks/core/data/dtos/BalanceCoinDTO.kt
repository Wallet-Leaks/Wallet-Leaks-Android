package org.tbm.walletleaks.core.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceCoinDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("price")
    val price: String
)