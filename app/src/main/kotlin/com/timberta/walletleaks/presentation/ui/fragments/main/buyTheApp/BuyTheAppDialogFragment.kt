package com.timberta.walletleaks.presentation.ui.fragments.main.buyTheApp

import android.content.*
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentBuyTheAppDialogBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragmentWithoutViewModel
import com.timberta.walletleaks.presentation.extensions.copyTheTextToClipboard
import com.timberta.walletleaks.presentation.extensions.showLongDurationSnackbar
import com.timberta.walletleaks.presentation.extensions.showShortDurationSnackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BuyTheAppDialogFragment :
    BaseDialogFragmentWithoutViewModel<FragmentBuyTheAppDialogBinding>(R.layout.fragment_buy_the_app_dialog) {
    override val binding by viewBinding(FragmentBuyTheAppDialogBinding::bind)

    override fun setupListeners() {
        openTelegramToBuyBaseVersion()
        openTelegramToBuyPremiumAccount()
    }

    private fun openTelegramToBuyBaseVersion() {
        binding.btnBaseAccount.setOnClickListener {
            try {
                copyTheTextToClipboard(
                    getString(R.string.base_version_label),
                    getString(R.string.i_want_to_buy_base_version)
                )
                Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("http://t.me/${getString(R.string.telegram_account)}")
                    showShortDurationSnackbar(getString(R.string.just_paste_the_text_from_your_clipboard))
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            delay(3000L)
                            startActivity(
                                Intent.createChooser(
                                    this@apply,
                                    "Choose your Telegram client"
                                )
                            )
                        }
                    }
                }
            } catch (e: ActivityNotFoundException) {
                showLongDurationSnackbar(getString(R.string.telegram_is_not_installed))
            }
        }
    }

    private fun openTelegramToBuyPremiumAccount() {
        binding.btnPremiumAccount.setOnClickListener {
            try {
                copyTheTextToClipboard(
                    getString(R.string.premium_version_label),
                    getString(R.string.i_want_to_buy_premium_version)
                )
                Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("http://t.me/${getString(R.string.telegram_account)}")
                    showShortDurationSnackbar(getString(R.string.just_paste_the_text_from_your_clipboard))
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            delay(3000L)
                            startActivity(
                                Intent.createChooser(
                                    this@apply,
                                    "Choose your Telegram client"
                                )
                            )
                        }
                    }
                }
            } catch (e: ActivityNotFoundException) {
                showLongDurationSnackbar(getString(R.string.telegram_is_not_installed))
            }
        }
    }
}