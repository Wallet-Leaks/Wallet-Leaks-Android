package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.repositories.UserRepository

class LogOutUseCase(private val userRepository: UserRepository) {
    operator fun invoke(refreshToken: String) =
        userRepository.logOut(refreshToken)
}