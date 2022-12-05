package com.timberta.walletleaks.data.repositories

import com.timberta.walletleaks.data.base.makeNetworkRequest
import com.timberta.walletleaks.data.remote.apiservices.UserApiService
import com.timberta.walletleaks.data.remote.dtos.RefreshTokenDto
import com.timberta.walletleaks.data.remote.dtos.toData
import com.timberta.walletleaks.domain.either.Either
import com.timberta.walletleaks.domain.models.GeneralUserInfoModel
import com.timberta.walletleaks.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userApiService: UserApiService,
) : UserRepository {

    override fun fetchUser(id: String) = makeNetworkRequest {
        userApiService.fetchUser(id).toDomain()
    }

    override fun logOut(refreshToken: String): Flow<Either<String, Unit>> = makeNetworkRequest {
        userApiService.logout(RefreshTokenDto(refreshToken)).body()
    }

    override fun modifyUserInfo(userInfoModel: GeneralUserInfoModel) = makeNetworkRequest {
        userApiService.modifyUserProfile(
            userInfoModel.toData()
        )
    }
}