package org.tbm.walletleaks.core.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationDTO(
    @SerialName("user")
    val user: UserDTO,
    @SerialName("refresh")
    val refresh: String,
    @SerialName("access")
    val access: String
)