package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refresh")
    val refreshToken: String
)