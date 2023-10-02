package org.tbm.walletleaks.core.data.local.preferences

import android.content.SharedPreferences
import org.tbm.walletleaks.core.data.base.local.Preferences

class UserPreferences(sharedPreferences: SharedPreferences) : Preferences(sharedPreferences) {

    var accessToken by string()

    var refreshToken by string()

    var user by nonPrimitive<User>()
}
data class User(
    val name: String,
    val age: Int
)