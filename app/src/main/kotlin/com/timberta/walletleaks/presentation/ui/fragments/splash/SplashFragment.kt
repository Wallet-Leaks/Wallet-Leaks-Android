package com.timberta.walletleaks.presentation.ui.fragments.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSplashBinding
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import com.timberta.walletleaks.presentation.extensions.postHandler
import com.timberta.walletleaks.presentation.extensions.renderTextColorUsingTwoColors
import com.timberta.walletleaks.presentation.extensions.transformTextFont
import org.koin.android.ext.android.inject

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val userDataPreferencesManager by inject<UserDataPreferencesManager>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderAppName()
        renderMadeBy()
        postHandler(1000L) {
            when (userDataPreferencesManager.isAuthenticated) {
                true ->
                    findNavController().navigateSafely(R.id.action_splashFragment_to_mainFlowFragment)
                false -> findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            }
        }
    }

    private fun renderAppName() {
        binding.tvAppTitle.renderTextColorUsingTwoColors()
    }

    private fun renderMadeBy() {
        binding.tvMadeBy.text =
            transformTextFont(getString(R.string.made_by_the_uss), R.font.mitr_medium, 4, 11)
    }
}