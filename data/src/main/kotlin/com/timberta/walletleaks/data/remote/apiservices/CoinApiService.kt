package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.remote.dtos.BasePagingResponse
import com.timberta.walletleaks.data.remote.dtos.CoinDto
import retrofit2.http.GET

interface CoinApiService {
    @GET("api/v1/wallets")
    suspend fun fetchCoins(): BasePagingResponse<CoinDto>
}