package com.timberta.walletleaks.presentation.models

import com.timberta.walletleaks.domain.models.TokensModel

data class TokensUI(
    val user: UserUI,
    val refresh: String,
    val access: String,
)

fun TokensModel.toUI() = TokensUI(user.toUI(), refresh, access)