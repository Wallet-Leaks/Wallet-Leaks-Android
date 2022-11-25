package com.timberta.walletleaks.presentation.ui.fragments.main.profile

import com.timberta.walletleaks.R
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.UserActionsInfoUI


class ProfileViewModel : BaseViewModel() {

    fun getUserActions(): ArrayList<UserActionsInfoUI> {
        return arrayListOf(
            UserActionsInfoUI("Withdrawal", R.drawable.ic_wallet_withdrawal, 1),
            UserActionsInfoUI("Premium+", R.drawable.ic_premium, 3),
            UserActionsInfoUI("Settings", R.drawable.ic_settings, 4),
            UserActionsInfoUI("Exit", R.drawable.ic_exit, 5)
        )
    }
}