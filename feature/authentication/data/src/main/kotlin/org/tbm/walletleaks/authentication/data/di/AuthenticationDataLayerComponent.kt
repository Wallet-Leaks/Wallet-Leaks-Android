package org.tbm.walletleaks.authentication.data.di

import dagger.Component
import org.tbm.walletleaks.authentication.data.di.modules.AuthenticationDataLayerRepositoryModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthenticationDataLayerRepositoryModule::class])
interface AuthenticationDataLayerComponent {


}