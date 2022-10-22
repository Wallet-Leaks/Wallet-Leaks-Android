package com.timberta.walletleaks.data.local.preferences

import android.content.SharedPreferences
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.DOES_USER_HAVE_PREMIUM
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.IS_USER_AUTHENTICATED
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.MINING_TIME_TIMER

class UserDataPreferencesManager constructor(private val preferences: SharedPreferences) {
    var isAuthenticated: Boolean
        get() = preferences.getBoolean(IS_USER_AUTHENTICATED, true)
        set(value) = preferences.edit().putBoolean(IS_USER_AUTHENTICATED, value).apply()

    var doesUserHavePremium: Boolean
        get() = preferences.getBoolean(DOES_USER_HAVE_PREMIUM, true)
        set(value) = preferences.edit().putBoolean(DOES_USER_HAVE_PREMIUM, value).apply()

    var miningTimeTimer: Long
        get() = preferences.getLong(MINING_TIME_TIMER, 0)
        set(value) = preferences.edit().putLong(MINING_TIME_TIMER, value).apply()

}