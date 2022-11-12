package com.timberta.walletleaks.presentation.ui.fragments.main.buyTheApp

import android.content.*
import android.view.KeyEvent
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentBuyTheAppDialogBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragmentWithoutViewModel
import com.timberta.walletleaks.presentation.extensions.copyTheTextToClipboard
import com.timberta.walletleaks.presentation.extensions.openTelegramBasedAppViaLink


class BuyTheAppDialogFragment :
    BaseDialogFragmentWithoutViewModel<FragmentBuyTheAppDialogBinding>(R.layout.fragment_buy_the_app_dialog) {
    override val binding by viewBinding(FragmentBuyTheAppDialogBinding::bind)

    override fun setupListeners() {
        openTelegramToBuyBaseVersion()
        openTelegramToBuyPremiumAccount()
        closeApplicationWhenBackButtonIsPressed()
    }

    private fun openTelegramToBuyBaseVersion() {
        binding.btnBaseAccount.setOnClickListener {
            openTelegramBasedAppViaLink {
                copyTheTextToClipboard(
                    getString(R.string.base_version_label),
                    getString(R.string.i_want_to_buy_base_version)
                )
            }
        }
    }

    private fun openTelegramToBuyPremiumAccount() {
        binding.btnPremiumAccount.setOnClickListener {
            openTelegramBasedAppViaLink {
                copyTheTextToClipboard(
                    getString(R.string.premium_version_label),
                    getString(R.string.i_want_to_buy_premium_version)
                )
            }
        }
    }

    private fun closeApplicationWhenBackButtonIsPressed() {
        requireDialog().setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == KeyEvent.ACTION_UP
            ) requireActivity().finish()
            true
        }
    }
}