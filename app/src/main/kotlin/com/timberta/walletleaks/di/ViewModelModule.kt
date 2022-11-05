package com.timberta.walletleaks.di

import com.timberta.walletleaks.presentation.ui.fragments.authentication.SignInViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.MainFlowViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.coinList.CoinListViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins.SelectCoinsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CoinListViewModel(get())
    }
    viewModel {
        SelectCoinsViewModel(get())
    }
    viewModel {
        SignInViewModel(get())
    }
    viewModel {
        MainFlowViewModel(get(), get())
    }
}