package com.timberta.walletleaks.domain

import com.timberta.walletleaks.domain.useCases.FetchCoinsUseCase
import com.timberta.walletleaks.domain.useCases.FetchUserUseCase
import com.timberta.walletleaks.domain.useCases.LogInUseCase
import com.timberta.walletleaks.domain.useCases.SignUpUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        LogInUseCase(get())
    }

    factory {
        SignUpUseCase(get())
    }

    factory {
        FetchUserUseCase(get())
    }

    factory {
        FetchCoinsUseCase(get())
    }
}