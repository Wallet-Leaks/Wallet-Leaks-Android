package com.timberta.walletleaks.presentation.ui.fragments.main.premium.dialog

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.DialogPremiumPurchaseBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment


class PremiumPurchaseDialog :
    BaseDialogFragment<DialogPremiumPurchaseBinding, PremiumPurchaseViewModel>(R.layout.dialog_premium_purchase) {

    override val binding by viewBinding(DialogPremiumPurchaseBinding::bind)
    override val viewModel by viewModels<PremiumPurchaseViewModel>()

}