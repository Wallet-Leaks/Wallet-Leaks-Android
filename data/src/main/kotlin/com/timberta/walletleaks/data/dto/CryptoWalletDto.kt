package com.timberta.walletleaks.data.dto

import com.timberta.walletleaks.domain.model.CryptoWallet

data class CryptoWalletDto(
    val address: String,
    val privateKey: String,
    val price: Double
)

fun CryptoWalletDto.toDomain() = CryptoWallet(address, privateKey, price)
