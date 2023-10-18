package org.tbm.walletleaks.authentication.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDTO(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("confirm_password")
    val confirmedPassword: String = password
)