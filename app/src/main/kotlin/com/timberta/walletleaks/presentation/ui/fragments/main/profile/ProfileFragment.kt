package com.timberta.walletleaks.presentation.ui.fragments.main.profile

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentProfileBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.*
import com.timberta.walletleaks.presentation.ui.adapters.UserActionsInfoAdapter
import kotlinx.coroutines.flow.collectLatest
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
            if (binding.tvAddressCryptoWallet.text.equals(R.string.add_a_crypto_wallet)) {
                actionProfileFragToProfileSettingsFrag()
            } else {
                copyTheTextToClipboard(
                    getString(R.string.cryptoAddress), binding.tvAddressCryptoWallet.text.toString()
                )
                showShortDurationSnackbar(getString(R.string.copy_address_clipboard))
            }
        }
    }

    override fun launchObservers() {
        subscribeUser()
        subscribeToOverallLoadingState()
    }

    private fun subscribeToOverallLoadingState() = with(binding.sflUserActionsList) {
        safeFlowGather {
            viewModel.overallLoadingState.collectLatest {
                when (it) {
                    0 -> startShimmer()
                    1 -> {
                        stopShimmer()
                        hideShimmer()
                        redrawViewsWhenShimmerIsInvisible()
                    }
                }
            }
        }
    }

    private fun redrawViewsWhenShimmerIsInvisible() {
        userActionsInfoAdapter.submitList(viewModel.getListMenuItem())
        binding.sflContainerUserInfo.gone()
        binding.sflUserActionsList.gone()
        binding.rvUserActionsInfo.visible()
        binding.containerUserInfo.visible()
    }

    private fun subscribeUser() {
        safeFlowGather {
            viewModel.userState.spectateUiState(success = {
                viewModel.modifyLoadingState()
                binding.tvNameUser.text = it.username
                binding.tvIdUser.text =
                    getString(R.string.user_id_multiplied, (it.id * 2222).toString())
                when (it.cryptoWalletAddress) {
                    "null" -> {
                        binding.tvAddressCryptoWallet.text = getString(R.string.add_a_crypto_wallet)
                    }
                    else -> binding.tvAddressCryptoWallet.text = it.cryptoWalletAddress
                }
            })
        }
    }

    private fun actionProfileFragToProfileSettingsFrag() {
        findNavController().directionsSafeNavigation(
            ProfileFragmentDirections.actionProfileFragmentToProfileSettingsFragment(
                binding.tvNameUser.text.toString(),
                binding.tvAddressCryptoWallet.text.toString()
            )
        )
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
                actionProfileFragToProfileSettingsFrag()
            }
            "Exit" -> {
                findNavController().navigateSafely(R.id.action_profileFragment_to_exitDialogFragment)
            }
        }
    }
}