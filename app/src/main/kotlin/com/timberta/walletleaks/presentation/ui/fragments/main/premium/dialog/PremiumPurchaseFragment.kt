package com.timberta.walletleaks.presentation.ui.fragments.main.premium.dialog

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentPremiumPurchaseBinding
import com.timberta.walletleaks.presentation.base.BaseDialogFragment
import com.timberta.walletleaks.presentation.base.BaseFragment


class PremiumPurchaseFragment :
    BaseDialogFragment<FragmentPremiumPurchaseBinding, PremiumPurchaseViewModel>(R.layout.fragment_premium_purchase) {

    override val binding by viewBinding(FragmentPremiumPurchaseBinding::bind)
    override val viewModel by viewModels<PremiumPurchaseViewModel>()

}