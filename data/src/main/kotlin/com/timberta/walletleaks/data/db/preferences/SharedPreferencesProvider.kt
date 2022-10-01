package com.timberta.walletleaks.data.db.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesProvider(private val context: Context) {

    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("data.db", Context.MODE_PRIVATE)
    }
}