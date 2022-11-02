package com.timberta.walletleaks.presentation.ui.fragments.main.profile.settings

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentProfileSettingsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment


class ProfileSettingsFragment :
    BaseFragment<FragmentProfileSettingsBinding, ProfileSettingsViewModel>(
        R.layout.fragment_profile_settings
    ) {

    override val binding by viewBinding(FragmentProfileSettingsBinding::bind)
    override val viewModel by viewModels<ProfileSettingsViewModel>()

    override fun initialize() {

    }

    override fun assembleViews() {

    }

    override fun constructListeners() {
        binding.toolbarSettings.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}