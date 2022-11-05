package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.remote.dtos.RefreshTokenDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshAccessTokenApiService {
    @POST("api/v1/users/refresh")
    fun refreshAccessToken(@Body refreshTokenDto: RefreshTokenDto): Call<String>
}