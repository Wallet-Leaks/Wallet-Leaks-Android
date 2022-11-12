package com.timberta.walletleaks.domain.repositories

import com.timberta.walletleaks.domain.either.Either
import com.timberta.walletleaks.domain.models.TokensModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun signUp(
        username: String,
        password: String,
        confirmationPassword: String
    ): Flow<Either<String, TokensModel>>

    fun logIn(username: String, password: String): Flow<Either<String, TokensModel>>
}