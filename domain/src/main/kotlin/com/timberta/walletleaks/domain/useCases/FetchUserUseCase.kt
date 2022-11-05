package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.repositories.UserRepository

class FetchUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String) = userRepository.fetchUser(id)
}