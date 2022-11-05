package com.timberta.walletleaks.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password2")
    val confirmationPassword: String
)