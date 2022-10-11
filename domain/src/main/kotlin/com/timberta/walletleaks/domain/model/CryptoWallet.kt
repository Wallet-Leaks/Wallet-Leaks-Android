package com.timberta.walletleaks.domain.model

data class CryptoWallet(
    val address: String,
    val privateKey: String,
    val price: Double
)
