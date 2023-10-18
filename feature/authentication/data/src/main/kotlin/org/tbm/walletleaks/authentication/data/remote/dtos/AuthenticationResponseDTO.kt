package org.tbm.walletleaks.authentication.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.tbm.walletleaks.core.data.dtos.UserDTO

@Serializable
data class AuthenticationResponseDTO(
    @SerialName("user")
    val user: UserDTO,
    @SerialName("refresh")
    val refresh: String,
    @SerialName("access")
    val access: String
)