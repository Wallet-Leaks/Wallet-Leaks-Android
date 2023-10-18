package org.tbm.walletleaks.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.tbm.walletleaks.core.data.local.preferences.UserPreferences
import org.tbm.walletleaks.core.presentation.ui.foundation.theming.WalletLeaksTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalletLeaksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}