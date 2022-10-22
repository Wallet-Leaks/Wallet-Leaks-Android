package com.timberta.walletleaks.data.remote.pagingSources

import com.timberta.walletleaks.data.base.BasePagingSource
import com.timberta.walletleaks.data.remote.apiservices.CoinApiService
import com.timberta.walletleaks.data.remote.dtos.CoinDto
import com.timberta.walletleaks.domain.models.CoinModel

class CoinsPagingSource(private val coinApiService: CoinApiService) :
    BasePagingSource<CoinDto, CoinModel>({
        coinApiService.fetchCoins(it)
    })