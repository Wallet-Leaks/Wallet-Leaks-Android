package com.timberta.walletleaks.presentation.ui.fragments.main.coinList

import com.timberta.walletleaks.domain.useCases.FetchCoinsUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CoinListViewModel(private val fetchCoinsUseCase: FetchCoinsUseCase) : BaseViewModel() {
    private val _coinsLoadingState = MutableStateFlow(false)
    val coinsLoadingState = _coinsLoadingState.asStateFlow()

    fun fetchCoins() =
        fetchCoinsUseCase().gatherPagingRequest { it.toUI() }

    fun modifyLoadingState() {
        _coinsLoadingState.value = true
    }


    init {
        fetchCoins()
    }
}