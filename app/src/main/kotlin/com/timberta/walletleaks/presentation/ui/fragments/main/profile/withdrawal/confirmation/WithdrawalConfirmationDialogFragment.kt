package com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal.confirmation

import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentWithdrawalConfirmationDialogBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import org.koin.androidx.viewmodel.ext.android.viewModel

class WithdrawalConfirmationDialogFragment :
    BaseDialogFragment<FragmentWithdrawalConfirmationDialogBinding, WithdrawalConfirmationViewModel>(
        R.layout.fragment_withdrawal_confirmation_dialog
    ) {
    override val binding by viewBinding(FragmentWithdrawalConfirmationDialogBinding::bind)
    override val viewModel by viewModel<WithdrawalConfirmationViewModel>()

    override fun constructListeners() {
        confirmWithdrawalAndNavigateToFragment()
    }

    private fun confirmWithdrawalAndNavigateToFragment() {
        findNavController().navigateSafely(R.id.action_withdrawalConfirmationDialogFragment_to_profileFragment)
    }
}