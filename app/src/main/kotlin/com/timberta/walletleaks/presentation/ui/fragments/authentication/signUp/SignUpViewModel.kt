package com.timberta.walletleaks.presentation.ui.fragments.authentication

import com.timberta.walletleaks.domain.useCases.SignUpUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.TokensUI
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {
    private val _signUpState = mutableUiStateFlow<TokensUI>()
    val signUpState = _signUpState.asStateFlow()

    fun signUp(username: String, password: String, confirmedPassword: String) =
        signUpUseCase(
            username,
            password,
            confirmedPassword
        ).gatherRequest(_signUpState) { it.toUI() }
}