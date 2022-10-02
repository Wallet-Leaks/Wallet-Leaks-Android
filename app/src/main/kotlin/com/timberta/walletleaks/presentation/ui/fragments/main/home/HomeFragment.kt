package com.timberta.walletleaks.presentation.ui.fragments.main.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.db.preferences.AccessPremiumPreferencesManager
import com.timberta.walletleaks.databinding.FragmentHomeBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()
    private val accessPremiumPreferencesManager: AccessPremiumPreferencesManager by inject()

    override fun initialize() {
        binding.root.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_premiumPurchaseFragment)
        }
    }
}