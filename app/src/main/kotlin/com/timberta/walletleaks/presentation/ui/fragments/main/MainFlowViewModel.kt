package com.timberta.walletleaks.presentation.ui.fragments.main

import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.UserUI
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.asStateFlow

class MainFlowViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : BaseViewModel() {

    private val _userState = mutableUiStateFlow<UserUI>()
    val userState = _userState.asStateFlow()

    fun fetchUser() =
        fetchUserUseCase(userDataPreferencesManager.userId.toString()).gatherRequest(_userState) { it.toUI() }

    init {
        fetchUser()
    }
}