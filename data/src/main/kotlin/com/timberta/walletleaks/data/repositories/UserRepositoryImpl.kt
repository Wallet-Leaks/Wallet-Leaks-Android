package com.timberta.walletleaks.data.repositories

import com.timberta.walletleaks.data.base.makeNetworkRequest
import com.timberta.walletleaks.data.remote.apiservices.UserApiService
import com.timberta.walletleaks.data.remote.dtos.RefreshTokenDto
import com.timberta.walletleaks.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userApiService: UserApiService,
) : UserRepository {

    override fun fetchUser(id: String) = makeNetworkRequest {
        userApiService.fetchUser(id).toDomain()
    }

    override fun logOut(refreshToken: String) = makeNetworkRequest {
        userApiService.logout(RefreshTokenDto(refreshToken))
    }
}