package com.timberta.walletleaks.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.data.utils.DataMapper
import com.timberta.walletleaks.domain.models.BalanceCoinModel

data class BalanceCoinDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: String
) : DataMapper<BalanceCoinModel> {
    override fun toDomain() = BalanceCoinModel(id, title, price)
}