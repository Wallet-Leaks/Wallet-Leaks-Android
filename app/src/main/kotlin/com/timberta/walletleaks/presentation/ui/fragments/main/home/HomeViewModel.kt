package com.timberta.walletleaks.presentation.ui.fragments.main.home

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.ui.model.CryptoWalletUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class HomeViewModel : BaseViewModel() {

    private val modelList = ArrayList<CryptoWalletUI>()
    private val _getListCryptoWalletsState = MutableStateFlow<List<CryptoWalletUI>>(emptyList())
    val getListCryptoWalletsState = _getListCryptoWalletsState.asStateFlow()

    private val _getTimeTimerText = MutableStateFlow("")
    val getTimeTimerText = _getTimeTimerText.asStateFlow()

    val processCryptoWorkState = MutableStateFlow(false)
    private var countDownTimer: CountDownTimer? = null
    private var mTimeInMillis: Long = 0

    var allPrice = 0.0
    var processIndex = 0

    fun searchCryptoWallets() {
        viewModelScope.launch {
            var coin = 0.0
            if (Random.nextInt(0, 100) == 1) {
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

    fun startTimer(timeInMillis: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeInMillis = millisUntilFinished
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
        val hours = (mTimeInMillis / (1000 * 60 * 60) % 24)
        val minutes = (mTimeInMillis / (1000 * 60)) % 60
        val seconds = (mTimeInMillis / 1000) % 60
        _getTimeTimerText.value =
            String.format(Locale.getDefault(), "%01d:%02d:%02d", hours, minutes, seconds)
    }
}