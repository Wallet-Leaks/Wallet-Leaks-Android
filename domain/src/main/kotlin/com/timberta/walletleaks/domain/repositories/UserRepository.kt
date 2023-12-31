package com.timberta.walletleaks.domain.repositories

import com.timberta.walletleaks.domain.either.Either
import com.timberta.walletleaks.domain.models.GeneralUserInfoModel
import com.timberta.walletleaks.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun fetchUser(id: String): Flow<Either<String, UserModel>>
    fun logOut(refreshToken: String): Flow<Either<String, Unit>>
    fun modifyUserInfo(userInfoModel: GeneralUserInfoModel): Flow<Either<String, Unit>>
}