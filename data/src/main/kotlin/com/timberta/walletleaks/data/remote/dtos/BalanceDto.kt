package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.data.utils.DataMapper
import com.timberta.walletleaks.domain.models.BalanceModel

data class BalanceDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("coin")
    val coin: BalanceCoinDto,
    @SerializedName("user")
    val user: Int,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("created")
    val created: String
) : DataMapper<BalanceModel>, UserInfo() {
    override fun toDomain() = BalanceModel(id, balance, created, coin.toDomain(), user)
}