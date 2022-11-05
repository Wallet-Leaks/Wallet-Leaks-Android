package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.remote.dtos.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("api/v1/users/{id}")
    suspend fun fetchUser(@Path("id") id: String): UserDto
}