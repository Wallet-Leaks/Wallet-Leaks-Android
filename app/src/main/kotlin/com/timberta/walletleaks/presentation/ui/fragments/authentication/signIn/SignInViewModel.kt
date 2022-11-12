package com.timberta.walletleaks.presentation.ui.fragments.authentication.signIn

import com.timberta.walletleaks.domain.useCases.LogInUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.TokensUI
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel(
    private val logInUseCase: LogInUseCase
) : BaseViewModel() {
    private val _logInState = mutableUiStateFlow<TokensUI>()
    val logInState = _logInState.asStateFlow()

    fun logIn(username: String, password: String) =
        logInUseCase(username, password).gatherRequest(_logInState) { it.toUI() }
}