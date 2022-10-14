package com.timberta.walletleaks.domain

import com.timberta.walletleaks.domain.useCases.FetchCoinsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        FetchCoinsUseCase(get())
    }
}