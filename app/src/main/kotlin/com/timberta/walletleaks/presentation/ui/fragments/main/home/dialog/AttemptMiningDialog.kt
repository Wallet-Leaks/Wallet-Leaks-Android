package com.timberta.walletleaks.presentation.ui.fragments.main.home.dialog

import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.DialogAttemptMiningBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AttemptMiningDialog : BaseDialogFragment<DialogAttemptMiningBinding, AttemptMiningViewModel>(
    R.layout.dialog_attempt_mining
) {

    override val binding by viewBinding(DialogAttemptMiningBinding::bind)
    override val viewModel by viewModel<AttemptMiningViewModel>()

    override fun initialize() {
        viewModel.startTimer()
    }

    override fun assembleViews() {
        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun constructListeners() {
        showDateAndTimeWhenUserWillHaveMiningAvailability()
        binding.btnOk.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun showDateAndTimeWhenUserWillHaveMiningAvailability() {
        binding.tvCanMineIn.text =
            getString(R.string.oops_you_have_already, viewModel.getTimeToWalletMineAgain())
    }
}