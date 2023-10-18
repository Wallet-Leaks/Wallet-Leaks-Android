package org.tbm.walletleaks.authentication.data.di.modules

import dagger.Binds
import dagger.Module
import org.tbm.walletleaks.authentication.data.repositories.AuthenticationRepositoryImpl
import org.tbm.walletleaks.authentication.domain.repositories.AuthenticationRepository

@Module
abstract class AuthenticationDataLayerRepositoryModule {

    @Binds
    abstract fun bindAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}