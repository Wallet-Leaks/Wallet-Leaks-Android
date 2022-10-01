package com.timberta.walletleaks.data.di

import android.content.SharedPreferences
import com.timberta.walletleaks.data.db.preferences.AccessPremiumPreferencesManager
import com.timberta.walletleaks.data.db.preferences.SharedPreferencesProvider
import org.koin.dsl.module

val dataModule = module {

    //Preferences
    single { SharedPreferencesProvider(get()) }
    single { get<SharedPreferencesProvider>().provideSharedPreferences() }
    single { provideAccessPreferencesManager(get()) }
}

private fun provideAccessPreferencesManager(preferences: SharedPreferences) =
    AccessPremiumPreferencesManager(preferences)