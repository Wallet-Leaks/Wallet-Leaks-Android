package com.timberta.walletleaks.data.db.preferences

import android.content.SharedPreferences
import com.timberta.walletleaks.data.db.preferences.PreferencesKeys.IS_SHOW_CHECK_ACCESS_PREMIUM_KEY

class AccessPremiumPreferencesManager constructor(private val preferences: SharedPreferences) {

    var isShowAccessPremium: Boolean
        get() = preferences.getBoolean(IS_SHOW_CHECK_ACCESS_PREMIUM_KEY, false)
        set(value) = preferences.edit().putBoolean(IS_SHOW_CHECK_ACCESS_PREMIUM_KEY, value).apply()
}