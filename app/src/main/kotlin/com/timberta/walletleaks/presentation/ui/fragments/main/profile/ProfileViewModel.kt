package com.timberta.walletleaks.presentation.ui.fragments.main.profile

import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.UserActionsInfoUI
import com.timberta.walletleaks.presentation.models.UserUI
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ProfileViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : BaseViewModel() {

    private val _userState = mutableUiStateFlow<UserUI>()
    val userState = _userState.asStateFlow()
    private val _overallLoadingState = MutableStateFlow(0)
    val overallLoadingState = _overallLoadingState.asStateFlow()

    private fun fetchUser() =
        fetchUserUseCase(userDataPreferencesManager.userId.toString()).gatherRequest(_userState) { it.toUI() }

    fun modifyLoadingState() {
        _overallLoadingState.value = 1
    }

    fun getListMenuItem(): List<UserActionsInfoUI> {
        return listOf(
            UserActionsInfoUI("Withdrawal", R.drawable.ic_wallet_withdrawal, 1),
            UserActionsInfoUI("Premium+", R.drawable.ic_premium, 3),
            UserActionsInfoUI("Settings", R.drawable.ic_settings, 4),
            UserActionsInfoUI("Exit", R.drawable.ic_exit, 5)
        )
    }

    init {
        fetchUser()
    }
}