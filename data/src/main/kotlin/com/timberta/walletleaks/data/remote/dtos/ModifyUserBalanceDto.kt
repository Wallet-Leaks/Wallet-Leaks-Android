package com.timberta.walletleaks.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.domain.models.ModifyUserBalanceModel

data class ModifyUserBalanceDto(
    @SerializedName("coin")
    val coin: Int,
    @SerializedName("balance")
    val balance: Double
)

fun ModifyUserBalanceModel.toData() = ModifyUserBalanceDto(coin, balance)