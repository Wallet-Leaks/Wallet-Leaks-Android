package com.timberta.walletleaks.data

import android.content.SharedPreferences
import com.timberta.walletleaks.data.local.preferences.SharedPreferencesProvider
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.data.remote.Authenticator
import com.timberta.walletleaks.data.remote.NetworkClient
import com.timberta.walletleaks.data.remote.interceptors.AuthenticationInterceptor
import com.timberta.walletleaks.data.remote.provideOkHttpClientBuilder
import com.timberta.walletleaks.data.remote.provideRetrofit
import com.timberta.walletleaks.data.repositories.AuthenticationRepositoryImpl
import com.timberta.walletleaks.data.repositories.CoinRepositoryImpl
import com.timberta.walletleaks.data.repositories.UserRepositoryImpl
import com.timberta.walletleaks.domain.repositories.AuthenticationRepository
import com.timberta.walletleaks.domain.repositories.CoinRepository
import com.timberta.walletleaks.domain.repositories.UserRepository
import org.koin.dsl.module

val dataModule = module {
    // Retrofit & OkHttp
    single { provideRetrofit(get()) }
    single { provideOkHttpClientBuilder().build() }
    single { AuthenticationInterceptor(get()) }
    single { Authenticator(get(), get()) }
    single { NetworkClient(get(), get()) }
    single { get<NetworkClient>().generateCoinApiService() }
    single { get<NetworkClient>().generateUserApiService() }
    single { NetworkClient.AuthenticationClient().generateAuthenticationApiService() }
    single { NetworkClient.AuthenticationClient().generateRefreshAccessTokenApiService() }

    //Preferences
    single { SharedPreferencesProvider(get()) }
    single { get<SharedPreferencesProvider>().provideSharedPreferences() }
    single { provideUserDataPreferencesManager(get()) }

    // Repositories
    single<CoinRepository> {
        CoinRepositoryImpl(get())
    }
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get(), get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}

private fun provideUserDataPreferencesManager(preferences: SharedPreferences) =
    UserDataPreferencesManager(preferences)