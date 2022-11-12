package com.timberta.walletleaks.di

import com.timberta.walletleaks.presentation.ui.fragments.authentication.signIn.SignInViewModel
import com.timberta.walletleaks.presentation.ui.fragments.authentication.signUp.SignUpViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.MainFlowViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.coinList.CoinListViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins.SelectCoinsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SignInViewModel(get())
    }

    viewModel {
        SignUpViewModel(get())
    }
    viewModel {
        MainFlowViewModel(get(), get())
    }

    viewModel {
        CoinListViewModel(get())
    }

    viewModel {
        SelectCoinsViewModel(get())
    }
}