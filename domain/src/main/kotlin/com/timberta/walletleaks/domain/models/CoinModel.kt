package com.timberta.walletleaks.domain.models

data class CoinModel(
    val id: Int,
    val title: String,
    val slug: String,
    val url: String,
    val price: String,
    val symbol: String,
    val icon: String
)