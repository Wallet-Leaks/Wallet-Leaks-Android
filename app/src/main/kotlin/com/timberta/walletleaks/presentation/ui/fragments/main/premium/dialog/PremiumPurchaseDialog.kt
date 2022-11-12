package com.timberta.walletleaks.presentation.ui.fragments.main.premium.dialog

import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.DialogPremiumPurchaseBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment
import com.timberta.walletleaks.presentation.extensions.copyTheTextToClipboard
import com.timberta.walletleaks.presentation.extensions.openTelegramBasedAppViaLink


class PremiumPurchaseDialog :
    BaseDialogFragment<DialogPremiumPurchaseBinding, PremiumPurchaseViewModel>(R.layout.dialog_premium_purchase) {

    override val binding by viewBinding(DialogPremiumPurchaseBinding::bind)
    override val viewModel by viewModels<PremiumPurchaseViewModel>()

    override fun initialize() {
        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun assembleViews() {
        binding.btnBuyPremium.text = setFontSizeForPath()
    }

    override fun setupListeners() {
        binding.btnCloseDialog.setOnClickListener {
            dialog?.cancel()
        }
        binding.btnBuyPremium.setOnClickListener {
            copyTheTextToClipboard(
                getString(R.string.premium_version_label),
                getString(R.string.i_want_to_buy_premium_version)
            )
            openTelegramBasedAppViaLink()
            findNavController().navigateUp()
        }
    }

    private fun setFontSizeForPath(
    ): SpannableString {
        val spannable = SpannableString(getString(R.string.btn_text_buy_premium))
        val startIndexOfPath = spannable.toString().indexOf(getString(R.string.premium_price))
        spannable.setSpan(
            AbsoluteSizeSpan(26),
            startIndexOfPath,
            startIndexOfPath + getString(R.string.premium_price).length,
            0
        )
        return spannable
    }
}