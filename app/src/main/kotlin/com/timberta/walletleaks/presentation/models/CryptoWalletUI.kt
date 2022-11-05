package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.presentation.base.BaseDiffModel

data class CryptoWalletUI(
    override val id: String,
    val privateKey: String,
    val price: Double
) : BaseDiffModel<String>

//fun CryptoWallet.toUI() = CryptoWalletUI(address, privateKey, price)

