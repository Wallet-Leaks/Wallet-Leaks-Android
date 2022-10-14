package com.timberta.walletleaks.domain.usecases

import com.timberta.walletleaks.domain.repositories.CryptoWalletsSearchRepository

class GetListCryptoWalletsUseCase(private val repository: CryptoWalletsSearchRepository) {

    //operator fun invoke() = repository.searchCryptoWallets()
}