package org.tbm.walletleaks.core.data.base.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO<T>(
    @SerialName("200")
    val code: Int,
    @SerialName("status")
    val status: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T
)