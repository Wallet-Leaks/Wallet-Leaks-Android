package com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal.confirmation

import com.timberta.walletleaks.domain.useCases.ModifyUserInfoUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.GeneralUserInfoUI
import com.timberta.walletleaks.presentation.models.toDomain
import kotlinx.coroutines.flow.asStateFlow

class WithdrawalConfirmationViewModel(
    private val modifyUserInfoUseCase: ModifyUserInfoUseCase
) :
    BaseViewModel() {
    private val _userBalanceModificationState = mutableUiStateFlow<Unit>()
    val userBalanceModificationState = _userBalanceModificationState.asStateFlow()

    fun modifyUserBalance(generalUserInfoUI: GeneralUserInfoUI) =
        modifyUserInfoUseCase(generalUserInfoUI.toDomain()).gatherRequest(
            _userBalanceModificationState
        )
}