package com.timberta.walletleaks.presentation.ui.fragments.main.settings

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentProfileBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.ui.adapters.UserActionsInfoAdapter

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    override val binding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel by viewModels<ProfileViewModel>()
    private val userActionsInfoAdapter = UserActionsInfoAdapter(this::onItemClick)

    override fun initialize() {
        binding.rvUserActionsInfo.adapter = userActionsInfoAdapter
        binding.rvUserActionsInfo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun assembleViews() {
        userActionsInfoAdapter.submitList(viewModel.getUserActions())
    }

    private fun onItemClick(actionName: String) {
        when (actionName) {
            "Premium+" -> {
                findNavController().navigate(R.id.premiumPurchaseDialog)
            }
        }
    }
}