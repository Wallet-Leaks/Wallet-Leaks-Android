package com.timberta.walletleaks.presentation.ui.fragments.main

import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentMainFlowBinding
import com.timberta.walletleaks.presentation.base.BaseFlowFragment
import com.timberta.walletleaks.presentation.extensions.gone
import com.timberta.walletleaks.presentation.extensions.visible


class MainFlowFragment :
    BaseFlowFragment(R.layout.fragment_main_flow, R.id.nav_host_fragment_container_main) {

    private val binding by viewBinding(FragmentMainFlowBinding::bind)

    override fun setupNavigation(navController: NavController) {
        constructBottomNavigation(navController)
        establishBottomNavigationRendering(navController)
    }

    private fun constructBottomNavigation(navController: NavController) {
        binding.bottomNavigation.itemIconTintList = null
        setupWithNavController(binding.bottomNavigation, navController)
    }

    private fun establishBottomNavigationRendering(navController: NavController) = with(binding) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.selectCoinsFragment -> {
                    mainToolbar.gone()
                    bottomNavigation.gone()
                }
                else -> {
                    mainToolbar.visible()
                    bottomNavigation.visible()
                }
            }
        }
    }
}