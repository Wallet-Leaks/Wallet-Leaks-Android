package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.remote.dtos.RefreshTokenDto
import com.timberta.walletleaks.data.remote.dtos.UserDto
import com.timberta.walletleaks.data.remote.dtos.UserInfo
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @GET("api/v1/users/{id}")
    suspend fun fetchUser(@Path("id") id: String): UserDto

    @PATCH("api/v1/users/profile/")
    suspend fun modifyUserProfile(@Body userInfo: UserInfo): UserDto

    @POST("api/v1/users/logout/")
    suspend fun logout(@Body refreshTokenDto: RefreshTokenDto): Response<Unit>
}