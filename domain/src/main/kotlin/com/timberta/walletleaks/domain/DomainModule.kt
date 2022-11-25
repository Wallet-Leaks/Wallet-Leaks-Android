package com.timberta.walletleaks.domain

import com.timberta.walletleaks.domain.useCases.*
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
    factory {
        LogOutUseCase(get())
    }

    factory {
        FetchCertainCoinsUseCase(get())
    }
}