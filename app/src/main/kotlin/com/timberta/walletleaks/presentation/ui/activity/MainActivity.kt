package com.timberta.walletleaks.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.db.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        when (userDataPreferencesManager.isAuthenticated) {
            true -> {
                navGraph.setStartDestination(R.id.mainFlowFragment)
            }
            false -> {
                navGraph.setStartDestination(R.id.signInFragment)
            }
        }
        navController.graph = navGraph
    }
}