package com.timberta.walletleaks.data.remote.dtos


import com.google.gson.annotations.SerializedName

data class BasePagingResponse<T>(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Int?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<T>
)