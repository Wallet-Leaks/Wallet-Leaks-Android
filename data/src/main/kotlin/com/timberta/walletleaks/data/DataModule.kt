package com.timberta.walletleaks.data

import android.content.SharedPreferences
import com.timberta.walletleaks.data.local.preferences.SharedPreferencesProvider
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.data.remote.NetworkClient
import com.timberta.walletleaks.data.remote.provideOkHttpClientBuilder
import com.timberta.walletleaks.data.remote.provideRetrofit
import com.timberta.walletleaks.data.repositories.CoinRepositoryImpl
import com.timberta.walletleaks.domain.repositories.CoinRepository
import org.koin.dsl.module

val dataModule = module {
    // Retrofit & OkHttp
    single { provideRetrofit(get()) }
    single { provideOkHttpClientBuilder().build() }
    single { NetworkClient().provideCoinApiService() }


    //Preferences
    single { SharedPreferencesProvider(get()) }
    single { get<SharedPreferencesProvider>().provideSharedPreferences() }
    single { provideAccessPreferencesManager(get()) }
    single<CoinRepository> {
        CoinRepositoryImpl(get())
    }
}

private fun provideAccessPreferencesManager(preferences: SharedPreferences) =
    UserDataPreferencesManager(preferences)