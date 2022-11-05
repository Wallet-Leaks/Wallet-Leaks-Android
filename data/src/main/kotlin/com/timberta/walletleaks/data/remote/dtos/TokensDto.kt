package com.timberta.walletleaks.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.timberta.walletleaks.data.utils.DataMapper
import com.timberta.walletleaks.domain.models.TokensModel

data class TokensDto(
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("access")
    val access: String
) : DataMapper<TokensModel> {
    override fun toDomain() = TokensModel(user.toDomain(), refresh, access)
}