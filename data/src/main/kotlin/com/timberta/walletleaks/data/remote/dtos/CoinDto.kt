package com.timberta.walletleaks.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.data.utils.DataMapper
import com.timberta.walletleaks.domain.models.CoinModel

data class CoinDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("isAvailable")
    val isAvailable: Boolean
) : DataMapper<CoinModel> {
    override fun toDomain() =
        CoinModel(id, title, slug, url, price, symbol, icon, rank, isAvailable)
}