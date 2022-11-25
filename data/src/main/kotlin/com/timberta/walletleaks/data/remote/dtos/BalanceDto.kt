package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.data.utils.DataMapper
import com.timberta.walletleaks.domain.models.BalanceModel

data class BalanceDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("created")
    val created: String,
    @SerializedName("coin")
    val coin: Int,
    @SerializedName("user")
    val user: Int
) : DataMapper<BalanceModel>, UserInfo() {
    override fun toDomain() = BalanceModel(id, balance, created, coin, user)
}