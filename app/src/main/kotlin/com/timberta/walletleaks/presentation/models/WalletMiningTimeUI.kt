package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.presentation.base.BaseDiffModel

data class WalletMiningTimeUI(
    override val id: Int,
    val time: Long,
    val isUnlocked: Boolean = true
) : BaseDiffModel<Int>
