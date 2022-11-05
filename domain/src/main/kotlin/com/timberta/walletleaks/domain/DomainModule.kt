package com.timberta.walletleaks.domain

import com.timberta.walletleaks.domain.useCases.FetchCoinsUseCase
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.domain.useCases.SignUpUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        FetchCoinsUseCase(get())
    }

    factory {
        SignUpUseCase(get())
    }

    factory {
        FetchUserUseCase(get())
    }
}