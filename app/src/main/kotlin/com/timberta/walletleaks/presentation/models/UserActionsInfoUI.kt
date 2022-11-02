package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.presentation.base.BaseDiffModel

data class UserActionsInfoUI(
    val actionName: String,
    val actionImage: Int,
    override val id: Int
) : BaseDiffModel<Int>
