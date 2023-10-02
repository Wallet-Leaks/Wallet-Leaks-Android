package org.tbm.walletleaks.authentication.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.tbm.walletleaks.core.domain.either.Either
import org.tbm.walletleaks.core.domain.either.NetworkError

interface AuthenticationRepository {
    fun signUp(username: String,password: String): Flow<Either<NetworkError,String>>
}