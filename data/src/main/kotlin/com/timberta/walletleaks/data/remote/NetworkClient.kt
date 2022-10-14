package com.timberta.walletleaks.data.remote

import com.timberta.walletleaks.data.remote.apiservices.CoinApiService
import retrofit2.Retrofit

class NetworkClient {
    private val retrofit =
        provideRetrofit(
            provideOkHttpClientBuilder().apply {
            }.build()
        )

    fun provideCoinApiService(): CoinApiService = retrofit.createAnApi()
}

inline fun <reified T : Any> Retrofit.createAnApi(): T = create(T::class.java)