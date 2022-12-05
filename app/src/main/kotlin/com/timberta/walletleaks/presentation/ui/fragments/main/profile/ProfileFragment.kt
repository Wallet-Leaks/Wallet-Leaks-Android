package com.timberta.walletleaks.presentation.ui.fragments.main.profile

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentProfileBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.*
import com.timberta.walletleaks.presentation.ui.adapters.UserActionsInfoAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    override val binding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel by viewModel<ProfileViewModel>()
    private val userActionsInfoAdapter = UserActionsInfoAdapter(this::onItemClick)

    override fun initialize() {
        binding.rvUserActionsInfo.adapter = userActionsInfoAdapter
        binding.rvUserActionsInfo.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun constructListeners() {
        binding.tvAddressCryptoWallet.setOnClickListener {
            copyTheTextToClipboard(
                getString(R.string.cryptoAddress), binding.tvAddressCryptoWallet.text.toString()
            )
            showShortDurationSnackbar("The address is copied to the clipboard")
        }
    }

    override fun launchObservers() {
        subscribeUser()
    }

    private fun subscribeUser() {
        safeFlowGather {
            viewModel.userState.spectateUiState(success = {
                binding.tvNameUser.text = it.username
                binding.tvIdUser.text = "ID: ${(it.id * 2222)}"
            }, gatherIfSucceed = {
                binding.progressCircular.bindToUIStateLoading(it)
            })
        }
    }

    private fun onItemClick(actionName: String) {
        when (actionName) {
            "Withdrawal" -> {
                findNavController().navigateSafely(R.id.action_profileFragment_to_withdrawalFragment)
            }
            "Premium+" -> {
                findNavController().navigate(R.id.premiumPurchaseDialog)
            }
            "Settings" -> {
                findNavController().directionsSafeNavigation(
                    ProfileFragmentDirections.actionProfileFragmentToProfileSettingsFragment(
                        binding.tvNameUser.text.toString()
                    )
                )
            }
            "Exit" -> {
                findNavController().navigateSafely(R.id.action_profileFragment_to_exitDialogFragment)
            }
        }
    }
}