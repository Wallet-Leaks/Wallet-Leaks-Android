package org.tbm.walletleaks.authentication.data.repositories

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import org.tbm.walletleaks.authentication.domain.repositories.AuthenticationRepository
import org.tbm.walletleaks.core.data.base.RepositoryImpl
import org.tbm.walletleaks.core.domain.either.Either
import org.tbm.walletleaks.core.domain.either.NetworkError
import javax.inject.Inject
import javax.inject.Named

class AuthenticationRepositoryImpl @Inject constructor(
    @Named("no authentication") private val httpClient: HttpClient
) : RepositoryImpl(), AuthenticationRepository {
    override fun signUp(username: String, password: String): Flow<Either<NetworkError, String>> {
        TODO("Not yet implemented")
    }
}