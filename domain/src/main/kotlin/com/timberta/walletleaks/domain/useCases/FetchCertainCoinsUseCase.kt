package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.repositories.CoinRepository

class FetchCertainCoinsUseCase(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(coinIds: String) = coinRepository.fetchCertainCoins(coinIds)
}