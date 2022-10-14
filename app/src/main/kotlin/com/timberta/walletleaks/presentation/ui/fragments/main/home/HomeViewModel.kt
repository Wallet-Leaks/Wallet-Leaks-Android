package com.timberta.walletleaks.presentation.ui.fragments.main.home

import androidx.lifecycle.viewModelScope
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.ui.model.CryptoWalletUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel : BaseViewModel() {

    private val modelList = ArrayList<CryptoWalletUI>()
    private val _getListCryptoWalletsState = MutableStateFlow<List<CryptoWalletUI>>(emptyList())
    val getListCryptoWalletsState = _getListCryptoWalletsState.asStateFlow()
    val processCryptoWorkState = MutableStateFlow(false)
    var allPrice = 0.0
    var processIndex = 0

    fun searchCryptoWallets() {
        viewModelScope.launch {
            var coin = 0.0
            if (Random.nextInt(0, 30000) == 1) {
                coin = Random.nextDouble(0.0000, 0.0008)
            }
            modelList.add(
                CryptoWalletUI(
                    "bc1q" + generateString(38), generateString(64), coin
                )
            )
            allPrice += coin
            _getListCryptoWalletsState.value = modelList
        }
    }

    private fun generateString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length).map { allowedChars.random() }.joinToString("")
    }
}