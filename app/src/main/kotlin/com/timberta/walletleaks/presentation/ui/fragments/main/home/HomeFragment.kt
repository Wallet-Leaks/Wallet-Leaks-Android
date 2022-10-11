package com.timberta.walletleaks.presentation.ui.fragments.main.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentHomeBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.ui.adapters.CryptoAlgorithmAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()
    private val adapter = CryptoAlgorithmAdapter()

    override fun initialize() {
        binding.rvCryptoOperationHome.adapter = adapter
    }

    override fun constructListeners() {
        binding.btnStartOperation.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    for (i in 1..10000) {
                        delay(300)
                        viewModel.searchCryptoWallets(i)
                        adapter.notifyItemInserted(i)
                        if (adapter.itemCount > 0) {
                            binding.rvCryptoOperationHome.scrollToPosition(adapter.itemCount - 1)
                        }
                        binding.price.text = String.format("%.4f", viewModel.allPrice)
                    }
                }
            }
        }
    }

    override fun launchObservers() {
        viewModel.viewModelScope.launch {
            viewModel.getListCryptoWalletsState.collect {
                adapter.submitList(it)
            }
        }
    }
}