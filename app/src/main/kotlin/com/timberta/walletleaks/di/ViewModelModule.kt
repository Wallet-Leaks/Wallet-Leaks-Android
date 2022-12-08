package com.timberta.walletleaks.di

import com.timberta.walletleaks.presentation.ui.fragments.authentication.signIn.SignInViewModel
import com.timberta.walletleaks.presentation.ui.fragments.authentication.signUp.SignUpViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.MainFlowViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.coinList.CoinListViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.home.HomeViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins.SelectCoinsViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.profile.ProfileViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.profile.exit.ExitViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.profile.settings.ProfileSettingsViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal.WithdrawalViewModel
import com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal.confirmation.WithdrawalConfirmationViewModel
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

    viewModel {
        WithdrawalViewModel(get(), get(), get())
    }

    viewModel {
        WithdrawalConfirmationViewModel(get())
    }

    viewModel {
        ExitViewModel(get())
    }

    viewModel {
        ProfileViewModel(get(), get())
    }

    viewModel {
        ProfileSettingsViewModel(get())
    }

    viewModel {
        HomeViewModel(get(), get(), get())
    }
}