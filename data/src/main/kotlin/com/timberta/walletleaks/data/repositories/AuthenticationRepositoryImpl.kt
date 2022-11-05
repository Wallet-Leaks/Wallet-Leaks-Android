package com.timberta.walletleaks.data.repositories

import com.timberta.walletleaks.data.base.makeNetworkRequest
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.data.remote.apiservices.AuthenticationApiService
import com.timberta.walletleaks.data.remote.dtos.SignUpDto
import com.timberta.walletleaks.domain.models.TokensModel
import com.timberta.walletleaks.domain.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val authenticationApiService: AuthenticationApiService,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : AuthenticationRepository {
    override fun signUp(
        username: String,
        password: String,
        confirmationPassword: String
    ) = makeNetworkRequest(this::saveTokensAndUserId) {
        authenticationApiService.signUp(
            SignUpDto(
                username,
                password,
                confirmationPassword
            )
        ).toDomain()
    }

    private fun saveTokensAndUserId(tokensModel: TokensModel) {
        userDataPreferencesManager.accessToken = tokensModel.access
        userDataPreferencesManager.refreshToken = tokensModel.refresh
        userDataPreferencesManager.userId = tokensModel.user.id
    }
}