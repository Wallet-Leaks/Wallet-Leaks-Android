package com.timberta.walletleaks.presentation.ui.fragments.main.profile.settings

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentProfileSettingsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.hideSoftKeyboard
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import com.timberta.walletleaks.presentation.extensions.showShortDurationSnackbar
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
        binding.imApply.isEnabled = false
        binding.etUsername.setText(args.username)
    }

    override fun constructListeners() {
        binding.apply {
            toolbarSettings.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            imApply.setOnClickListener {
                if (etUsername.text.toString() != args.username) {
                    hideSoftKeyboard()
                    viewModel.changeUserName(
                        GeneralUserInfoUI(
                            userDataPreferencesManager.userId,
                            etUsername.text.toString(),
                            balance = emptyList()
                        )
                    )
                }
            }
            binding.etUsername.addTextChangedListener {
                if (etUsername.text.toString() != args.username) {
                    imApply.isEnabled = true
                    imApply.alpha = 1.0F
                } else {
                    imApply.isEnabled = false
                    imApply.alpha = 0.5F
                }
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