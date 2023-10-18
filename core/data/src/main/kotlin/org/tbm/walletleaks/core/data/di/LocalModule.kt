package org.tbm.walletleaks.core.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalModule {
    @Singleton
    @Provides
    fun generateSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            "wallet_leaks_preferences",
            Context.MODE_PRIVATE
        )
}