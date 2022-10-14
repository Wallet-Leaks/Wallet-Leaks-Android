package com.timberta.walletleaks.presentation.ui.fragments.main.coinList

import com.timberta.walletleaks.domain.useCases.FetchCoinsUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.toUI

class CoinListViewModel(private val fetchCoinsUseCase: FetchCoinsUseCase) : BaseViewModel() {

    fun fetchCoins() =
        fetchCoinsUseCase().gatherPagingRequest { it.toUI() }


    init {
        fetchCoins()
    }
}