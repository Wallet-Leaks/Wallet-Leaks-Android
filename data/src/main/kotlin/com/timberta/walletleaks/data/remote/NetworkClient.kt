package com.timberta.walletleaks.data.remote

import com.timberta.walletleaks.data.remote.apiservices.AuthenticationApiService
import com.timberta.walletleaks.data.remote.apiservices.CoinApiService
import com.timberta.walletleaks.data.remote.apiservices.RefreshAccessTokenApiService
import com.timberta.walletleaks.data.remote.apiservices.UserApiService
import com.timberta.walletleaks.data.remote.interceptors.AuthenticationInterceptor
import retrofit2.Retrofit

class NetworkClient(
    private val authenticationInterceptor: AuthenticationInterceptor,
    authenticator: Authenticator
) {

    private val retrofit =
        provideRetrofit(
            provideOkHttpClientBuilder().apply {
                authenticator(authenticator)
                addInterceptor(authenticationInterceptor)
            }.build()
        )

    fun generateCoinApiService() = retrofit.createAnApi<CoinApiService>()
    fun generateUserApiService() = retrofit.createAnApi<UserApiService>()

    class AuthenticationClient {
        private val retrofit = provideRetrofit(provideOkHttpClientBuilder().build())

        fun generateAuthenticationApiService() = retrofit.createAnApi<AuthenticationApiService>()

        fun generateRefreshAccessTokenApiService() =
            retrofit.createAnApi<RefreshAccessTokenApiService>()
    }
}

private inline fun <reified T : Any> Retrofit.createAnApi(): T = create(T::class.java)