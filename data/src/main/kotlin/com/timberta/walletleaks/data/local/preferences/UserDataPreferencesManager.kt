package com.timberta.walletleaks.data.local.preferences

import android.content.SharedPreferences
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.ACCESS_TOKEN
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.DOES_USER_HAVE_PREMIUM
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.HAS_NON_PREMIUM_USER_TRIED_TO_SELECT_MULTIPLE_COINS
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.IS_USER_AUTHENTICATED
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.MINING_AVAILABILITY
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.MINING_TIME_PAUSE_TIMER
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.MINING_TIME_TIMER
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.REFRESH_TOKEN
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.USER_ID
import com.timberta.walletleaks.data.local.preferences.PreferencesKeys.lAST_SELECTED_POSITION

class UserDataPreferencesManager constructor(private val preferences: SharedPreferences) {
    var isAuthenticated: Boolean
        get() = preferences.getBoolean(IS_USER_AUTHENTICATED, false)
        set(value) = preferences.put(IS_USER_AUTHENTICATED, value)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(value) = preferences.put(ACCESS_TOKEN, value.toString())

    var refreshToken: String?
        get() = preferences.getString(REFRESH_TOKEN, "")
        set(value) = preferences.put(REFRESH_TOKEN, value.toString())

    var userId: Int
        get() = preferences.getInt(USER_ID, -1)
        set(value) = preferences.put(USER_ID, value)

    var doesUserHavePremium: Boolean
        get() = preferences.getBoolean(DOES_USER_HAVE_PREMIUM, false)
        set(value) = preferences.put(DOES_USER_HAVE_PREMIUM, value)

    private var hasNonPremiumUserTriedToSelectMultipleCoins: Boolean
        get() = preferences.getBoolean(HAS_NON_PREMIUM_USER_TRIED_TO_SELECT_MULTIPLE_COINS, false)
        set(value) = preferences.put(HAS_NON_PREMIUM_USER_TRIED_TO_SELECT_MULTIPLE_COINS, value)

    var timeLeftToMine: Long
        get() = preferences.getLong(MINING_TIME_TIMER, 0)
        set(value) = preferences.put(MINING_TIME_TIMER, value)

    var selectedTimeToMine: Long
        get() = preferences.getLong(MINING_TIME_PAUSE_TIMER, 0)
        set(value) = preferences.put(MINING_TIME_PAUSE_TIMER, value)

    var miningAvailability: Boolean
        get() = preferences.getBoolean(MINING_AVAILABILITY, false)
        set(value) = preferences.put(MINING_AVAILABILITY, value)


    var lastSelectedPosition: Int
        get() = preferences.getInt(lAST_SELECTED_POSITION, 0)
        set(value) = preferences.put(lAST_SELECTED_POSITION, value)

    fun actionIfNonPremiumUserSelectsMultipleCoinsForTheFirstTime(action: () -> Unit) {
        if (!hasNonPremiumUserTriedToSelectMultipleCoins && !doesUserHavePremium) {
            action()
            hasNonPremiumUserTriedToSelectMultipleCoins = true
        }
    }

    fun logOut() {
        preferences.clear()
    }
}