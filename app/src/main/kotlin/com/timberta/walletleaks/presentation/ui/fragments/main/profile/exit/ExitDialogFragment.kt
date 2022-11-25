package com.timberta.walletleaks.presentation.ui.fragments.main.profile.exit

import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentExitDialogBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment
import com.timberta.walletleaks.presentation.extensions.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExitDialogFragment :
    BaseDialogFragment<FragmentExitDialogBinding, ExitViewModel>(R.layout.fragment_exit_dialog) {
    override val binding by viewBinding(FragmentExitDialogBinding::bind)
    override val viewModel by viewModel<ExitViewModel>()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()

    override fun constructListeners() {
        dismissDialog()
        logOut()
    }

    private fun dismissDialog() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun logOut() {
        binding.btnLogOut.setOnClickListener {
            viewModel.logOut(userDataPreferencesManager.refreshToken.toString())
        }
    }

    override fun launchObservers() {
        subscribeToLogOut()
    }

    private fun subscribeToLogOut() {
        viewModel.logOutState.spectateUiState(success = {
//            userDataPreferencesManager.logOut()
            flowNavController(R.id.nav_host_fragment).navigateSafely(R.id.action_mainFlowFragment_to_signInFragment)
        }, gatherIfSucceed = {
            binding.cpiLogOut.bindToUIStateLoading(it)
            when (binding.cpiLogOut.isVisible) {
                true -> binding.btnLogOut.text = ""
                false -> binding.btnLogOut.text =
                    getString(R.string.log_out)
            }
        })
    }
}