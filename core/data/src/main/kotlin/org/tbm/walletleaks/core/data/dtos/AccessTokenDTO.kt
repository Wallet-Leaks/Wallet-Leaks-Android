package org.tbm.walletleaks.core.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenDTO(
    @SerialName("access")
    val accessToken: String
)