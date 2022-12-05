package com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal

import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.domain.useCases.FetchCertainCoinsUseCase
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.presentation.base.BaseViewModel
import com.timberta.walletleaks.presentation.models.CoinUI
import com.timberta.walletleaks.presentation.models.UserUI
import com.timberta.walletleaks.presentation.models.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WithdrawalViewModel(
    private val fetchCertainCoinsUseCase: FetchCertainCoinsUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : BaseViewModel() {

    private val _certainCoinsUseCase = mutableUiStateFlow<List<CoinUI>>()
    val certainCoinsState = _certainCoinsUseCase.asStateFlow()

    private val _userState = mutableUiStateFlow<UserUI>()
    val userState = _userState.asStateFlow()
    private val _overallLoadingState = MutableStateFlow(0)
    val overallLoadingState = _overallLoadingState.asStateFlow()

    private fun fetchCertainCoins() =
        fetchCertainCoinsUseCase(
            "1,2,4,5"
        ).gatherRequest(_certainCoinsUseCase) { it.map { coinModels -> coinModels.toUI() } }


    private fun fetchUser() =
        fetchUserUseCase(userDataPreferencesManager.userId.toString()).gatherRequest(_userState) { it.toUI() }

    fun modifyLoadingState() {
        _overallLoadingState.value = _overallLoadingState.value + 1
    }

    init {
        fetchCertainCoins()
        fetchUser()
    }
}