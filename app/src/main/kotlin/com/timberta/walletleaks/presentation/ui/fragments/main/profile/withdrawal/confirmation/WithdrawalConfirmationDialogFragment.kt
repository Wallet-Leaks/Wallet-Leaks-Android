package com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal.confirmation

import android.media.MediaPlayer
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentWithdrawalConfirmationDialogBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment
import com.timberta.walletleaks.presentation.extensions.bindToUIStateLoading
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import com.timberta.walletleaks.presentation.extensions.postHandler
import com.timberta.walletleaks.presentation.extensions.visible
import com.timberta.walletleaks.presentation.models.CardProcessingNetwork
import com.timberta.walletleaks.presentation.models.GeneralUserInfoUI
import com.timberta.walletleaks.presentation.models.ModifyUserBalanceUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class WithdrawalConfirmationDialogFragment :
    BaseDialogFragment<FragmentWithdrawalConfirmationDialogBinding, WithdrawalConfirmationViewModel>(
        R.layout.fragment_withdrawal_confirmation_dialog
    ) {
    override val binding by viewBinding(FragmentWithdrawalConfirmationDialogBinding::bind)
    override val viewModel by viewModel<WithdrawalConfirmationViewModel>()
    private val args by navArgs<WithdrawalConfirmationDialogFragmentArgs>()

    override fun initialize() {
        makeCancelable()
    }

    private fun makeCancelable() {
        requireDialog().setCancelable(true)
        requireDialog().setCanceledOnTouchOutside(true)
    }

    override fun assembleViews() {
        renderAmountToWithdrawAndCardProcessingNetworkAlongWithCardNumber()
    }

    private fun renderAmountToWithdrawAndCardProcessingNetworkAlongWithCardNumber() =
        with(binding) {
            when (args.cardProcessingNetwork) {
                CardProcessingNetwork.VISA -> imCardProcessingNetwork.setImageResource(R.drawable.ic_visa)
                else -> imCardProcessingNetwork.setImageResource(R.drawable.ic_master_card)
            }
            tvMoneyCount.text = args.withdrawalAmountInUsd
            tvWithdrawToFollowingCardProcessingNetwork.text = args.cardNumber
            tvAmountToWithdraw.text =
                getString(R.string.withdrawal_amount_in_usd, args.withdrawalAmountInUsd)
        }

    override fun constructListeners() {
        dismissDialog()
        confirmWithdrawalAndNavigateToProfile()
    }

    private fun dismissDialog() {
        listOf(binding.imClose, binding.btnChange).forEach {
            it.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun confirmWithdrawalAndNavigateToProfile() {
        binding.btnConfirm.setOnClickListener {
            viewModel.withdraw(
                GeneralUserInfoUI(
                    balance = listOf(
                        ModifyUserBalanceUI(
                            args.cryptoToWithdrawId,
                            args.withdrawalAmountInCrypto.toDouble()
                        )
                    )
                )
            )
        }
    }

    override fun launchObservers() {
        subscribeToWithdrawal()
    }

    private fun subscribeToWithdrawal() = with(binding) {
        viewModel.withdrawalState.spectateUiState(success = {
            btnConfirm.text = ""
            btnConfirm.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lola))
            lavSuccess.visible()
            lavSuccess.playAnimation()
            postHandler(1000L) {
                MediaPlayer.create(requireContext(), R.raw.success_sound_pro).start()
                postHandler(1500L) {
                    findNavController().navigateSafely(R.id.action_withdrawalConfirmationDialogFragment_to_profileFragment)
                }
            }

        }, gatherIfSucceed = {
            cpiWithdrawalConfirmation.bindToUIStateLoading(it)
            when (cpiWithdrawalConfirmation.isVisible) {
                true -> btnConfirm.text = ""
                false -> btnConfirm.text = getString(R.string.confirm)
            }
        })
    }
}