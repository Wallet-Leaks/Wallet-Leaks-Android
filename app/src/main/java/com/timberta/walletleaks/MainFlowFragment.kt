package com.timberta.walletleaks

import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.databinding.FragmentMainFlowBinding
import com.timberta.walletleaks.presentation.base.BaseFlowFragment


class MainFlowFragment : BaseFlowFragment(R.layout.fragment_main_flow, R.id.nav_host_fragment) {

    private val binding by viewBinding(FragmentMainFlowBinding::bind)

    override fun setupNavigation(navController: NavController) {
        initNavigation(navController)
    }

    private fun initNavigation(navController: NavController) {
        binding.bottomNavigation.itemIconTintList = null
        setupWithNavController(binding.bottomNavigation, navController)
    }
}