package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class AccessTokenDto(
    @SerializedName("access")
    val accessToken: String
)
