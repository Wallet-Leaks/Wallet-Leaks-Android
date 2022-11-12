package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class LogInDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
)