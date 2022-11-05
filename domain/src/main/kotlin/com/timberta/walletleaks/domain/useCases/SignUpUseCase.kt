package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.repositories.AuthenticationRepository

class SignUpUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(username: String, password: String, confirmationPassword: String) =
        authenticationRepository.signUp(username, password, confirmationPassword)
}