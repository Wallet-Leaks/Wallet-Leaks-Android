package com.timberta.walletleaks.data.base


import com.google.gson.annotations.SerializedName

data class BasePagingResponse<T>(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<T>
)