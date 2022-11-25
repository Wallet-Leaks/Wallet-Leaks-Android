package com.timberta.walletleaks.domain.useCases

import com.timberta.walletleaks.domain.models.GeneralUserInfoModel
import com.timberta.walletleaks.domain.repositories.UserRepository

class ModifyUserInfoUseCase constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(generalUserInfoModel: GeneralUserInfoModel) =
        userRepository.modifyUserInfo(generalUserInfoModel)
}