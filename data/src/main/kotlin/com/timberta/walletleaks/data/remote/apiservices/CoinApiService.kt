package com.timberta.walletleaks.data.remote.apiservices

import com.timberta.walletleaks.data.base.BasePagingResponse
import com.timberta.walletleaks.data.remote.dtos.CoinDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApiService {
    @GET("api/v1/wallets/wallet/")
    suspend fun fetchCoins(
        @Query("page") page: Int = 1,
        @Query("ids", encoded = true) coinIds: String = ""
    ): BasePagingResponse<CoinDto>
}