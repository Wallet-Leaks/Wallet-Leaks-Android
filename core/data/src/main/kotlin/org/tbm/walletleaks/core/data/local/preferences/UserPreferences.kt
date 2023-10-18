package org.tbm.walletleaks.core.data.local.preferences

import android.content.SharedPreferences
import org.tbm.walletleaks.core.data.base.local.Preferences

class UserPreferences(sharedPreferences: SharedPreferences) : Preferences(sharedPreferences) {

    var isAuthenticated by boolean()
}