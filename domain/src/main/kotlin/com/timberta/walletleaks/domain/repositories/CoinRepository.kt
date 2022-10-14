package com.timberta.walletleaks.domain.repositories

import androidx.paging.PagingData
import com.timberta.walletleaks.domain.models.CoinModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun fetchCoins(): Flow<PagingData<CoinModel>>
}