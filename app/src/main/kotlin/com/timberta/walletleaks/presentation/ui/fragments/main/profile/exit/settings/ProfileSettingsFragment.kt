package com.timberta.walletleaks.presentation.ui.fragments.main.profile.exit.settings

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentProfileSettingsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.*
import com.timberta.walletleaks.presentation.models.GeneralUserInfoUI
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileSettingsFragment :
    BaseFragment<FragmentProfileSettingsBinding, ProfileSettingsViewModel>(
        R.layout.fragment_profile_settings
    ) {
    override val binding by viewBinding(FragmentProfileSettingsBinding::bind)
    override val viewModel by viewModel<ProfileSettingsViewModel>()
    private val args by navArgs<ProfileSettingsFragmentArgs>()
    private val userDataPreferencesManager by inject<UserDataPreferencesManager>()

    override fun assembleViews() {
        binding.apply {
            toolbar.imApply.isEnabled = false
            toolbar.imApply.visible()
            etUsername.setText(args.username)
            if (args.cryptoWalletsAddress != getString(R.string.add_a_crypto_wallet)) {
                etCryptocurrencyAddress.setText(args.cryptoWalletsAddress)
            }
        }
    }

    override fun constructListeners() {
        binding.apply {
            toolbar.mtToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.imApply.setOnClickListener {
                if (etUsername.text.toString() != args.username) {
                    hideSoftKeyboard()
                    viewModel.changeUserName(
                        GeneralUserInfoUI(
                            userDataPreferencesManager.userId,
                            username = etUsername.text.toString(),
                            balance = emptyList()
                        )
                    )
                } else if (etCryptocurrencyAddress.text.toString() != args.cryptoWalletsAddress) {
                    hideSoftKeyboard()
                    viewModel.changeUserName(
                        GeneralUserInfoUI(
                            userDataPreferencesManager.userId,
                            cryptoWalletAddress = etCryptocurrencyAddress.text.toString(),
                            balance = emptyList()
                        )
                    )
                }
            }

            imCopyPast.setOnClickListener {
                copyTheTextToClipboard(
                    getString(R.string.cryptoAddress),
                    binding.etCryptocurrencyAddress.text.toString()
                )
                showShortDurationSnackbar("The address is copied to the clipboard")
            }

            etCryptocurrencyAddress.addTextChangedListener {
                enabledApplyButton()
            }
            etUsername.addTextChangedListener {
                enabledApplyButton()
            }
        }
    }

    private fun enabledApplyButton() = with(binding) {
        val cryptoAddress = etCryptocurrencyAddress.text.toString()
        toolbar.imApply.apply {
            if (etUsername.text.toString() != args.username || cryptoAddress != args.cryptoWalletsAddress) {
                isEnabled = true
                alpha = 1.0F
            } else {
                isEnabled = false
                alpha = 0.5F
            }
        }
    }

    override fun launchObservers() {
        subscribeToUsernameModification()
    }

    private fun subscribeToUsernameModification() {
        viewModel.usernameModificationState.spectateUiState(success = {
            findNavController().navigateSafely(R.id.action_profileSettingsFragment_to_profileFragment)
        }, error = {
            showShortDurationSnackbar(it)
        })
    }
}