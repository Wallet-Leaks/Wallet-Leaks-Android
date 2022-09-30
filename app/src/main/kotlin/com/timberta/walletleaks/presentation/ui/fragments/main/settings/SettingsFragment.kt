package com.timberta.walletleaks.presentation.ui.fragments.main.settings

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentSettingsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewModel>(R.layout.fragment_settings) {

    override val binding by viewBinding(FragmentSettingsBinding::bind)
    override val viewModel by viewModels<SettingsViewModel>()
}