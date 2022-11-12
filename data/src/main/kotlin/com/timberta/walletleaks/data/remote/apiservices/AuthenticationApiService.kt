package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.remote.dtos.LogInDto
import com.timberta.walletleaks.data.remote.dtos.SignUpDto
import com.timberta.walletleaks.data.remote.dtos.TokensDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApiService {
    @POST("api/v1/users/register/")
    suspend fun signUp(@Body signUpDto: SignUpDto): TokensDto

    @POST("api/v1/users/login/")
    suspend fun logIn(@Body logInDto: LogInDto): TokensDto
}