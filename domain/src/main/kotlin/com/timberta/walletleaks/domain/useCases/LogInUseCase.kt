package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.repositories.AuthenticationRepository

class LogInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(username: String, password: String) =
        authenticationRepository.logIn(username, password)
}