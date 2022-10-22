package com.timberta.walletleaks.data.local.preferences

import android.content.SharedPreferences
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.DOES_USER_HAVE_PREMIUM
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.HAS_NON_PREMIUM_USER_TRIED_TO_SELECT_MULTIPLE_COINS
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.IS_USER_AUTHENTICATED

class UserDataPreferencesManager constructor(private val preferences: SharedPreferences) {
    var isAuthenticated: Boolean
        get() = preferences.getBoolean(IS_USER_AUTHENTICATED, false)
        set(value) = preferences.put(IS_USER_AUTHENTICATED, value)

    var doesUserHavePremium: Boolean
        get() = preferences.getBoolean(DOES_USER_HAVE_PREMIUM, false)
        set(value) = preferences.put(DOES_USER_HAVE_PREMIUM, value)

    var hasNonPremiumUserTriedToSelectMultipleCoins: Boolean
        get() = preferences.getBoolean(HAS_NON_PREMIUM_USER_TRIED_TO_SELECT_MULTIPLE_COINS, false)
        set(value) = preferences.put(HAS_NON_PREMIUM_USER_TRIED_TO_SELECT_MULTIPLE_COINS, value)

    fun actionIfNonPremiumUserSelectsMultipleCoinsForTheFirstTime(action: () -> Unit) {
        if (!hasNonPremiumUserTriedToSelectMultipleCoins && !doesUserHavePremium) {
            action()
            hasNonPremiumUserTriedToSelectMultipleCoins = true
        }
    }
}