package com.timberta.walletleaks.di

import com.timberta.walletleaks.domain.usecases.GetListCryptoWalletsUseCase
import com.timberta.walletleaks.presentation.ui.fragments.main.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainModule = module {
    factory { GetListCryptoWalletsUseCase(get()) }
}