package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.repositories.CoinRepository

class FetchCoinsUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke() = coinRepository.fetchCoins()
}