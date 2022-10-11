package com.timberta.walletleaks.presentation.ui.model

import com.timberta.walletleaks.domain.model.CryptoWallet
import com.timberta.walletleaks.presentation.base.BaseDiffModel

data class CryptoWalletUI(
    override val address: String,
    val privateKey: String,
    val price: Double
) : BaseDiffModel<String>

fun CryptoWallet.toUI() = CryptoWalletUI(address, privateKey, price)

