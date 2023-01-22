package com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSelectCoinsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.base.BaseLoadStateAdapter
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
        binding.toolbar.imApply.isEnabled = false
        binding.toolbar.imApply.visible()
        coinListAdapter.startSelectingCoins()
    }

    private fun constructRecycler() = with(binding.rvCoinList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = coinListAdapter.withLoadStateFooter(BaseLoadStateAdapter())
        itemAnimator = null
        setItemViewCacheSize(100)
        coinListAdapter.bindViewsToPagingLoadStates(
            recyclerView = this,
            shimmerFrameLayout = binding.sflSelectCoins,
            viewsToBindToLoadStateNotLoading = arrayOf(
                binding.toolbar.imApply,
                binding.imLock,
                binding.tvComingSoon
            )
        )
    }

    override fun assembleViews() {
        setToolbarTitle()
    }

    private fun setToolbarTitle() {
        binding.toolbar.mtToolbar.title = getString(R.string.coins)
    }

    override fun constructListeners() {
        overrideOnBackPressedToHideToolbar()
        navigateBackToHomeFragment()
        navigateToHomeFragmentWithSelectedCoins()
    }

    private fun overrideOnBackPressedToHideToolbar() {
        overrideOnBackPressed {
            setBackStackData("coinSelection", true)
        }
    }

    private fun navigateBackToHomeFragment() {
        binding.toolbar.mtToolbar.setNavigationOnClickListener {
            setBackStackData("coinSelection", true)
        }
    }

    private fun navigateToHomeFragmentWithSelectedCoins() {
        binding.toolbar.imApply.setOnClickListener {
            observeNetworkConnectionStatusAndAction(actionWhenConnected = {
                findNavController().directionsSafeNavigation(
                    SelectCoinsFragmentDirections.actionSelectCoinsFragmentToHomeFragment(
                        coinListAdapter.getSelectedCoins()
                    )
                )
            }, actionWhenDisconnected = {
                showCustomToast(getString(R.string.cant_select_coins_due_to_lost_internet_connection))
            })
        }
    }

    override fun launchObservers() {
        observeNetworkConnectionStatusAndAction(actionWhenConnected = {
            subscribeToCoins()
        }, actionWhenDisconnected = {
            if (coinListAdapter.snapshot().items.isEmpty())
                showCustomToast(getString(R.string.unable_to_load_coins))
        })
    }

    private fun subscribeToCoins() {
        viewModel.fetchCoins().spectatePaging {
            coinListAdapter.submitData(it)
        }
    }

    private fun onItemClick(coinsCount: Int, hasUserTriedToSelectMultipleCoins: Boolean) =
        with(binding) {
            toolbar.imApply.isEnabled = coinsCount >= 1
            toolbar.imApply.alpha = when (toolbar.imApply.isEnabled) {
                true -> 1.0F
                false -> 0.5F
            }
            if (hasUserTriedToSelectMultipleCoins)
                userDataPreferencesManager.actionIfNonPremiumUserSelectsMultipleCoinsForTheFirstTime {
                    findNavController().navigate(R.id.premiumPurchaseDialog)
                }
        }
}