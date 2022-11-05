package com.timberta.walletleaks.domain.models

data class TokensModel(
    val user: UserModel,
    val refresh: String,
    val access: String
)