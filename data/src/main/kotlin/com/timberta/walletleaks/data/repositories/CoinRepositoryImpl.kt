package com.timberta.walletleaks.data.repositories

import com.timberta.walletleaks.data.base.makeNetworkRequest
import com.timberta.walletleaks.data.base.makePagingRequest
import com.timberta.walletleaks.data.remote.apiservices.CoinApiService
import com.timberta.walletleaks.data.remote.pagingSources.CoinsPagingSource
import com.timberta.walletleaks.domain.repositories.CoinRepository

class CoinRepositoryImpl(private val coinApiService: CoinApiService) : CoinRepository {

    override fun fetchCoins() = makePagingRequest(CoinsPagingSource(coinApiService))

    override fun fetchCertainCoins(coinIds: String) = makeNetworkRequest {
        coinApiService.fetchCoins(coinIds = coinIds).results.map { it.toDomain() }
    }
}