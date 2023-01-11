package com.timberta.walletleaks.presentation.ui.fragments.main.profile.exit.settings

import com.timberta.walletleaks.domain.useCases.ModifyUserInfoUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.GeneralUserInfoUI
import com.timberta.walletleaks.presentation.models.toDomain
import kotlinx.coroutines.flow.asStateFlow

class ProfileSettingsViewModel(
    private val modifyUserInfoUseCase: ModifyUserInfoUseCase,
) : BaseViewModel() {

    private val _usernameModificationState = mutableUiStateFlow<Unit>()
    val usernameModificationState = _usernameModificationState.asStateFlow()

    fun changeUserName(generalUserInfoUI: GeneralUserInfoUI) =
        modifyUserInfoUseCase(generalUserInfoUI.toDomain()).gatherRequest(_usernameModificationState)
}