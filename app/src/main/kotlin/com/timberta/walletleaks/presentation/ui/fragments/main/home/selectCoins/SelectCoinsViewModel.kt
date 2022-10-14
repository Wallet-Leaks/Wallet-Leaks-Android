package com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins

import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.CoinUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectCoinsViewModel :
    BaseViewModel() {
    private val _coinsState = MutableStateFlow<List<CoinUI>>(emptyList())
    val coinsState = _coinsState.asStateFlow()

    private fun createCoinsList() {
        _coinsState.value =
            listOf(
                CoinUI(
                    id = 1,
                    title = "Bitcoin",
                    symbol = "BTC",

                    ),
                CoinUI(id = 2, title = "Ethereum", symbol = "ETH"),
                CoinUI(
                    id =
                    3,
                    title = "Binance Coin",
                    symbol = "BNB",
                ),
                CoinUI(
                    id = 4,
                    title = "Litecoin",
                    symbol = "LTC",
                ),
                CoinUI(
                    id = 5,
                    title = "Tether",
                    symbol = "USDT",
                    isAvailable = false
                ),
                CoinUI(
                    id = 6,
                    symbol = "ADA",
                    title = "Cardano",
                    isAvailable = false
                ),
                CoinUI(
                    id = 7,
                    symbol = "SOL",
                    title = "Solana",
                    isAvailable = false
                ),
                CoinUI(
                    id = 8,
                    symbol = "AVAX",
                    title = "Avalanche",
                    isAvailable = false
                ),
                CoinUI(
                    id = 9,
                    symbol = "CHZ",
                    title = "Chillz",
                    isAvailable = false
                ),
                CoinUI(
                    id = 10,
                    symbol = "LUNC",
                    title = "Terra Classic",
                    isAvailable = false
                )
            )
    }

    init {
        createCoinsList()
    }
}