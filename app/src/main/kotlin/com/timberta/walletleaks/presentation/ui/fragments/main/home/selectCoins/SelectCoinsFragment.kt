package com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSelectCoinsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.*
import com.timberta.walletleaks.presentation.ui.adapters.CoinListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectCoinsFragment :
    BaseFragment<FragmentSelectCoinsBinding, SelectCoinsViewModel>(R.layout.fragment_select_coins) {
    override val binding by viewBinding(FragmentSelectCoinsBinding::bind)
    override val viewModel by viewModel<SelectCoinsViewModel>()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private val coinListAdapter =
        CoinListAdapter(userDataPreferencesManager.doesUserHavePremium, this::onItemClick)

    override fun initialize() {
        constructRecycler()
        coinListAdapter.startSelectingCoins()
    }

    private fun constructRecycler() = with(binding.rvCoinList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = coinListAdapter
        itemAnimator = null
        coinListAdapter.bindViewsToPagingLoadStates(
            this,
            binding.cpiCoinList,
            binding.imSoon,
            binding.tvSoon,
        )
    }

    override fun constructListeners() {
        navigateBackToHomeFragment()
        navigateToHomeFragmentWithSelectedCoins()
        overrideOnBackPressedToHideToolbar()
    }

    private fun overrideOnBackPressedToHideToolbar() = with(binding) {
        overrideOnBackPressed {
            makeMultipleViewsInvisible(
                toolbarCoinList,
                cpiCoinList,
                vToolbarStroke,
                imSoon,
                tvSoon
            )
            findNavController().navigateUp()
        }
    }

    private fun navigateBackToHomeFragment() {
        binding.toolbarCoinList.setNavigationOnClickListener {
            binding.toolbarCoinList.invisible()
            findNavController().navigateSafely(R.id.action_selectCoinsFragment_to_homeFragment)
        }
    }

    private fun navigateToHomeFragmentWithSelectedCoins() {
        binding.imApply.setOnClickListener {
            binding.toolbarCoinList.gone()
            findNavController().directionsSafeNavigation(
                SelectCoinsFragmentDirections.actionSelectCoinsFragmentToHomeFragment(
                    coinListAdapter.getSelectedCoins()
                )
            )
        }
    }

    override fun launchObservers() {
        subscribeToCoins()
    }

    private fun subscribeToCoins() {
        viewModel.fetchCoins().spectatePaging {
            coinListAdapter.submitData(it)
        }
    }

    private fun onItemClick(coinsCount: Int, hasUserTriedToSelectMultipleCoins: Boolean) {
        binding.imApply.isVisible = coinsCount >= 1
        if (hasUserTriedToSelectMultipleCoins)
            userDataPreferencesManager.actionIfNonPremiumUserSelectsMultipleCoinsForTheFirstTime {
                findNavController().navigate(R.id.premiumPurchaseFragment)
            }
    }
}