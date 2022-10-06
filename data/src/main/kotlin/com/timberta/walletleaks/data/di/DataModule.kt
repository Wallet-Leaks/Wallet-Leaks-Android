package com.timberta.walletleaks.data.di

import android.content.SharedPreferences
import com.timberta.walletleaks.data.db.preferences.SharedPreferencesProvider
import com.timberta.walletleaks.data.db.preferences.UserDataPreferencesManager
import org.koin.dsl.module

val dataModule = module {

    //Preferences
    single { SharedPreferencesProvider(get()) }
    single { get<SharedPreferencesProvider>().provideSharedPreferences() }
    single { provideAccessPreferencesManager(get()) }
}

private fun provideAccessPreferencesManager(preferences: SharedPreferences) =
    UserDataPreferencesManager(preferences)