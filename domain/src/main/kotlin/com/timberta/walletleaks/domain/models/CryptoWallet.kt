package com.timberta.walletleaks.domain.models

data class CryptoWallet(
    val address: String,
    val privateKey: String,
    val price: Double
)
