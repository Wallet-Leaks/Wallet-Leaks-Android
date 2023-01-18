package com.timberta.walletleaks.presentation.ui.fragments.main.coinList

import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentCoinListBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.bindViewsToPagingLoadStates
import com.timberta.walletleaks.presentation.extensions.observeNetworkConnectionStatusAndAction
import com.timberta.walletleaks.presentation.extensions.postHandler
import com.timberta.walletleaks.presentation.extensions.showCustomToast
import com.timberta.walletleaks.presentation.ui.adapters.CoinListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoinListFragment :
    BaseFragment<FragmentCoinListBinding, CoinListViewModel>(R.layout.fragment_coin_list) {

    override val binding by viewBinding(FragmentCoinListBinding::bind)
    override val viewModel by viewModel<CoinListViewModel>()

    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private val coinListAdapter =
        CoinListAdapter(userDataPreferencesManager.doesUserHavePremium)

    override fun initialize() {
        constructAdapter()
    }

    private fun constructAdapter() {
        binding.rvCoinList.adapter = coinListAdapter
        coinListAdapter.bindViewsToPagingLoadStates(
            binding.rvCoinList,
            shimmerFrameLayout = binding.sflCoinList,
            smartRefreshLayout = binding.srlCoinList
        )
        binding.rvCoinList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun constructListeners() {
        handleSwipeToRefresh()
    }

    private fun handleSwipeToRefresh() {
        binding.srlCoinList.setRefreshHeader(binding.srlChCoinList)
        binding.srlCoinList.setOnRefreshListener {
            postHandler(1500L) {
                observeNetworkConnectionStatusAndAction(actionWhenConnected = {
                    subscribeToCoinList()
                }, actionWhenDisconnected = {
                    showCustomToast(getString(R.string.unable_to_refresh_coins))
                })
                binding.srlCoinList.finishRefresh()
            }
        }
    }

    override fun launchObservers() {
        observeNetworkConnectionStatusAndAction(actionWhenConnected = {
            subscribeToCoinList()
        }, actionWhenDisconnected = {
            if (coinListAdapter.snapshot().items.isEmpty()) {
                binding.srlCoinList.isEnabled = false
                showCustomToast(getString(R.string.unable_to_load_coins))
            }
        })
    }

    private fun subscribeToCoinList() {
        viewModel.fetchCoins().spectatePaging {
            coinListAdapter.submitData(it)
        }
    }
}