package com.timberta.walletleaks.presentation.ui.fragments.main.coinList

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentCoinListBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import org.koin.dsl.module


class CoinListFragment :
    BaseFragment<FragmentCoinListBinding, CoinListViewModel>(R.layout.fragment_coin_list) {

    override val binding by viewBinding(FragmentCoinListBinding::bind)
    override val viewModel by viewModels<CoinListViewModel>()

}