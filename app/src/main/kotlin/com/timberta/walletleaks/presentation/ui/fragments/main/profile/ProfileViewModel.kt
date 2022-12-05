package com.timberta.walletleaks.presentation.ui.fragments.main.profile

import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.UserActionsInfoUI
import com.timberta.walletleaks.presentation.models.UserUI
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.asStateFlow


class ProfileViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : BaseViewModel() {

    private val _userState = mutableUiStateFlow<UserUI>()
    val userState = _userState.asStateFlow()

    private fun fetchUser() =
        fetchUserUseCase(userDataPreferencesManager.userId.toString()).gatherRequest(_userState) { it.toUI() }

    init {
        fetchUser()
    }
}