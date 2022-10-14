package com.timberta.walletleaks.presentation.ui.fragments.main.home.selectCoins

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSelectCoinsBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.directionsSafeNavigation
import com.timberta.walletleaks.presentation.ui.adapters.CoinListAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class SelectCoinsFragment :
    BaseFragment<FragmentSelectCoinsBinding, SelectCoinsViewModel>(R.layout.fragment_select_coins) {
    override val binding by viewBinding(FragmentSelectCoinsBinding::bind)
    override val viewModel by viewModels<SelectCoinsViewModel>()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private val coinListAdapter =
        CoinListAdapter(userDataPreferencesManager.doesUserHavePremium)

    override fun initialize() {
        constructRecycler()
        coinListAdapter.startSelectingCoins()
    }

    private fun constructRecycler() = with(binding.rvCoinList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = coinListAdapter
        itemAnimator = null
    }

    override fun constructListeners() {
        navigateBackToHomeFragment()
        navigateToHomeFragmentWithSelectedCoins()
    }

    private fun navigateBackToHomeFragment() {
        binding.toolbarCoinList.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateToHomeFragmentWithSelectedCoins() {
        binding.imApply.setOnClickListener {
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.coinsState.collect {
                    coinListAdapter.submitData(PagingData.from(it))
                }
            }
        }
    }
}