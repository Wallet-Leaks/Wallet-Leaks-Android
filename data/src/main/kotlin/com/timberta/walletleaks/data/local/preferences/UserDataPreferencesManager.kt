package com.timberta.walletleaks.data.local.preferences

import android.content.SharedPreferences
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.DOES_USER_HAVE_PREMIUM
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.IS_USER_AUTHENTICATED

class UserDataPreferencesManager constructor(private val preferences: SharedPreferences) {
    var isAuthenticated: Boolean
        get() = preferences.getBoolean(IS_USER_AUTHENTICATED, false)
        set(value) = preferences.edit().putBoolean(IS_USER_AUTHENTICATED, value).apply()

    var doesUserHavePremium: Boolean
        get() = preferences.getBoolean(DOES_USER_HAVE_PREMIUM, true)
        set(value) = preferences.edit().putBoolean(DOES_USER_HAVE_PREMIUM, value).apply()
}