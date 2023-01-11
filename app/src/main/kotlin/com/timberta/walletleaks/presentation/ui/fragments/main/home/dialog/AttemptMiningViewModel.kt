package com.timberta.walletleaks.presentation.ui.fragments.main.home.dialog

import android.os.CountDownTimer
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.extensions.loge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class AttemptMiningViewModel(
    private val userDataPreferencesManager: UserDataPreferencesManager
) : BaseViewModel() {

    private val _getTimeTimerTextState = MutableStateFlow("")
    val getTimeTimerTextState = _getTimeTimerTextState.asStateFlow()
    private var countDownTimer: CountDownTimer? = null


    fun startTimer() {
        loge(userDataPreferencesManager.timeLeftToMine.toString())
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(userDataPreferencesManager.timeLeftToMine, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateCountDownText(millisUntilFinished)
                loge(userDataPreferencesManager.timeLeftToMine.toString())
            }

            override fun onFinish() {
                pauseTimer()
            }
        }.start()
    }

    fun pauseTimer() {
        countDownTimer?.cancel()
    }

    fun updateCountDownText(millisUntilFinished: Long) {
        val hours = (millisUntilFinished / (1000 * 60 * 60) % 24)
        val minutes = (millisUntilFinished / (1000 * 60)) % 60
        val seconds = (millisUntilFinished / 1000) % 60
        _getTimeTimerTextState.value =
            String.format(Locale.getDefault(), "%01d:%02d:%02d", hours, minutes, seconds)
    }

    fun getTimeToWalletMineAgain(): String {
        val simpleDateFormat = SimpleDateFormat("dd.MMM.yyyy HH:mm:ss", Locale.UK)
        val date = Date(userDataPreferencesManager.timeLeftToMine + 86400000)
        return simpleDateFormat.format(date)
    }
}