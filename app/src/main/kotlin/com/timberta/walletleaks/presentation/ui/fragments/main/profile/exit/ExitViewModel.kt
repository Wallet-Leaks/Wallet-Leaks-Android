package com.timberta.walletleaks.presentation.ui.fragments.main.profile.exit

import com.timberta.walletleaks.domain.useCases.LogOutUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.asStateFlow

class ExitViewModel(
    private val logOutUseCase: LogOutUseCase,
) : BaseViewModel() {

    private val _logOutState = mutableUiStateFlow<Unit>()
    val logOutState = _logOutState.asStateFlow()

    fun logOut(refreshToken: String) =
        logOutUseCase(refreshToken).gatherRequest(_logOutState)
}