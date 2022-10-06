package com.timberta.walletleaks.data.db.preferences

import android.content.SharedPreferences
import com.timberta.walletleaks.data.db.preferences.PreferencesKeys.DOES_USER_HAVE_PREMIUM
import com.timberta.walletleaks.data.db.preferences.PreferencesKeys.IS_USER_AUTHENTICATED

class UserDataPreferencesManager constructor(private val preferences: SharedPreferences) {

    var isAuthenticated: Boolean
        get() = preferences.getBoolean(IS_USER_AUTHENTICATED, false)
        set(value) = preferences.edit().putBoolean(IS_USER_AUTHENTICATED, value).apply()

    var doesUserHavePremium: Boolean
        get() = preferences.getBoolean(DOES_USER_HAVE_PREMIUM, false)
        set(value) = preferences.edit().putBoolean(DOES_USER_HAVE_PREMIUM, value).apply()

}