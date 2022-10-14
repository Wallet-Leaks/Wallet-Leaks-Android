package com.timberta.walletleaks.data.local.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesProvider(private val context: Context) {

    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(
            "timberta.walletleaks.preferences",
            Context.MODE_PRIVATE
        )
    }
}