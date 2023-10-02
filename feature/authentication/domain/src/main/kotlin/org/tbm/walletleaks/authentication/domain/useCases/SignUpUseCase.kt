package org.tbm.walletleaks.authentication.domain.useCases

import org.tbm.walletleaks.authentication.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
}