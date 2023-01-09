package com.timberta.walletleaks.presentation.ui.fragments.main.home

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.domain.useCases.ModifyUserInfoUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.extensions.loge
import com.timberta.walletleaks.presentation.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class HomeViewModel(
    private val modifyUserInfoUseCase: ModifyUserInfoUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : BaseViewModel() {

    private val modelList = ArrayList<CryptoWalletUI>()
    private val _getListCryptoWalletsState = MutableStateFlow<List<CryptoWalletUI>>(emptyList())
    val getListCryptoWalletsState = _getListCryptoWalletsState.asStateFlow()

    private val _balanceModificationState = mutableUiStateFlow<Unit>()
    val balanceModificationState = _balanceModificationState.asStateFlow()

    private val _getTimeTimerTextState = MutableStateFlow("")
    val getTimeTimerTextState = _getTimeTimerTextState.asStateFlow()

    private val _getUserTimerMineWorkState =
        MutableStateFlow(userDataPreferencesManager.miningTimePauseTimer)
    val getUserTimerMineWorkState = _getUserTimerMineWorkState.asStateFlow()

    private val _userState = mutableUiStateFlow<UserUI>()
    val userState = _userState.asStateFlow()

    val processCryptoWorkState = MutableStateFlow(false)
    val coinsSelectionState = MutableStateFlow(false)
    private var countDownTimer: CountDownTimer? = null
    var processIndex = 0

    fun searchCryptoWallets(symbols: CoinUI) {
        viewModelScope.launch {
            _getListCryptoWalletsState.value = when (symbols.symbol) {
                "BTC" -> defineCryptoWalletsAddress("bc1q", 111.0, symbols.id)
                "ETH" -> defineCryptoWalletsAddress("0x", 222.0, symbols.id)
                "BNB" -> defineCryptoWalletsAddress("bnb1", 555.0, symbols.id)
                else -> defineCryptoWalletsAddress("ltc1q", 777.0, symbols.id)
            }
        }
    }

    private fun generateString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length).map { allowedChars.random() }.joinToString("")
    }

    private fun fetchUser() =
        fetchUserUseCase(userDataPreferencesManager.userId.toString()).gatherRequest(_userState) {
            it.toUI()
        }

    fun startTimer(timeInMillis: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                loge(millisUntilFinished.toString())
                userDataPreferencesManager.miningTimePauseTimer = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                updateCountDownText()
            }
        }.start()
    }

    fun pauseTimer() {
        countDownTimer?.cancel()
    }

    fun updateCountDownText() {
        val hours = (userDataPreferencesManager.miningTimePauseTimer / (1000 * 60 * 60) % 24)
        val minutes = (userDataPreferencesManager.miningTimePauseTimer / (1000 * 60)) % 60
        val seconds = (userDataPreferencesManager.miningTimePauseTimer / 1000) % 60
        _getUserTimerMineWorkState.value = userDataPreferencesManager.miningTimePauseTimer
        _getTimeTimerTextState.value =
            String.format(Locale.getDefault(), "%01d:%02d:%02d", hours, minutes, seconds)
    }

    private fun defineCryptoWalletsAddress(
        nameAddressCoin: String, maximumCountAvailableToMine: Double, coinUIId: Int
    ): ArrayList<CryptoWalletUI> {
        var coin = 0.0
        if (Random.nextInt(0, 100) == 1) {
            coin = Random.nextDouble(0.0000, maximumCountAvailableToMine)
            loge(coin.toString())
            fetchUser()
            userState.spectateUiState(success = { userUI ->
                userUI.balance.forEach {
                    if (coinUIId == it.coin.id) {
                        changeUserBalance(
                            GeneralUserInfoUI(
                                balance = listOf(
                                    ModifyUserBalanceUI(
                                        coinUIId, it.balance + coin
                                    )
                                )
                            )
                        )
                        return@forEach
                    }
                }
            })
        }
        modelList.add(
            CryptoWalletUI(
                nameAddressCoin + generateString(38), generateString(64), coin
            )
        )
        return modelList
    }

    private fun changeUserBalance(modifyUserBalance: GeneralUserInfoUI) =
        modifyUserInfoUseCase(modifyUserBalance.toDomain()).gatherRequest(
            _balanceModificationState
        )
}