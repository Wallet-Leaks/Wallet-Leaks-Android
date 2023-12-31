package com.timberta.walletleaks.presentation.ui.fragments.main

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentMainFlowBinding
import com.timberta.walletleaks.presentation.base.BaseFlowFragment
import com.timberta.walletleaks.presentation.extensions.gone
import com.timberta.walletleaks.presentation.extensions.postHandler
import com.timberta.walletleaks.presentation.extensions.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFlowFragment :
    BaseFlowFragment(R.layout.fragment_main_flow, R.id.nav_host_fragment_container_main) {

    private val binding by viewBinding(FragmentMainFlowBinding::bind)
    private val viewModel by viewModel<MainFlowViewModel>()
    private var isUserVerified: Boolean? = null
    private val networkRequestHandler = Looper.myLooper()?.let { Handler(it) }
    private val fetchUserTask = object : Runnable {
        override fun run() {
            viewModel.fetchUser()
            networkRequestHandler?.postDelayed(this, 30000L)
        }
    }
    private val userDataPreferencesManager by inject<UserDataPreferencesManager>()

    override fun setupNavigation(navController: NavController) {
        fetchCurrentUserAndNavigateToBuyTheAppDialogIfOneIsNotVerified(navController)
        constructBottomNavigation(navController)
        establishBottomNavigationRendering(navController)
    }

    private fun constructBottomNavigation(navController: NavController) {
        binding.bottomNavigation.itemIconTintList = null
        setupWithNavController(binding.bottomNavigation, navController)
    }

    private fun establishBottomNavigationRendering(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.selectCoinsFragment, R.id.profileSettingsFragment, R.id.withdrawalFragment, R.id.withdrawalConfirmationDialogFragment -> renderToolbarAndBottomNavigation(
                    false
                )
                R.id.premiumPurchaseDialog -> renderToolbarAndBottomNavigation(navController.previousBackStackEntry?.destination?.id == R.id.homeFragment || navController.previousBackStackEntry?.destination?.id == R.id.profileFragment)
                else -> renderToolbarAndBottomNavigation(true)
            }
        }
    }

    private fun renderToolbarAndBottomNavigation(shouldBeVisible: Boolean) = with(binding) {
        when (shouldBeVisible) {
            true -> {
                mainToolbar.visible()
                bottomNavigation.visible()
            }
            false -> {
                postHandler(80L) {
                    mainToolbar.gone()
                    bottomNavigation.gone()
                }
            }
        }
    }

    private fun fetchCurrentUserAndNavigateToBuyTheAppDialogIfOneIsNotVerified(navController: NavController) {
        binding.tvBalance.setOnClickListener {
            userDataPreferencesManager.selectedTimeToMine = 0
        }
        viewModel.userState.spectateUiState(success = {
            ValueAnimator.ofFloat(
                0.0f,
                it.totalBalance.toFloat()
            ).apply {
                duration = 2500
                addUpdateListener { animation ->
                    binding.tvBalance.text = getString(
                        R.string.money_with_dollar_sign_reversed,
                        animation.animatedValue.toString()
                    )
                }
                start()
            }
            userDataPreferencesManager.doesUserHavePremium = it.isPremium
            if (isUserVerified == null || isUserVerified != it.isVerified) {
                isUserVerified = it.isVerified
                when (it.isVerified) {
                    false -> navController.navigate(R.id.buyTheAppDialogFragment)
                    true ->
                        if (navController.currentDestination?.id == R.id.buyTheAppDialogFragment)
                            navController.navigate(R.id.homeFragment)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        networkRequestHandler?.post(fetchUserTask)
    }

    override fun onPause() {
        super.onPause()
        networkRequestHandler?.removeCallbacks(fetchUserTask)
    }
}