package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.remote.dtos.RefreshTokenDto
import com.timberta.walletleaks.data.remote.dtos.UserInfo
import com.timberta.walletleaks.data.remote.dtos.UserDto
import retrofit2.http.*

interface UserApiService {

    @GET("api/v1/users/{id}")
    suspend fun fetchUser(@Path("id") id: String): UserDto

    @POST("api/v1/users/logout/")
    suspend fun logout(@Body refreshTokenDto: RefreshTokenDto)

    @PATCH("api/v1/users/profile/")
    suspend fun modifyUserProfile(@Body userInfo: UserInfo): UserDto
}