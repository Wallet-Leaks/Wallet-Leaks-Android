package com.timberta.walletleaks.presentation.ui.fragments.main.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentHomeBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.invisible
import com.timberta.walletleaks.presentation.extensions.visible
import com.timberta.walletleaks.presentation.ui.adapters.CryptoAlgorithmAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()
    private val adapter = CryptoAlgorithmAdapter()

    override fun initialize() {
        viewModel.processCryptoWorkState.value = false
        binding.rvCryptoOperationHome.adapter = adapter
        containerOperationIsVisible()
    }

    override fun constructListeners() {
        binding.btnStartOperation.setOnClickListener {
            viewModel.processCryptoWorkState.value = true
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    for (i in viewModel.processIndex..10000) {
                        if (viewModel.processCryptoWorkState.value) {
                            viewModel.processIndex = adapter.itemCount
                            viewModel.searchCryptoWallets()
                            adapter.notifyItemInserted(viewModel.processIndex)
                            updateAdapterScroll()
                            binding.price.text =
                                String.format("%.4f", viewModel.allPrice * 19147.50)
                            viewModel.processIndex = i
                        } else {
                            break
                        }
                    }
                }
            }
        }
        binding.btnStopSearch.setOnClickListener {
            viewModel.processCryptoWorkState.value = false
        }
    }

    override fun launchObservers() {
        collectCryptoAlgorithm()
    }

    private fun collectCryptoAlgorithm() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getListCryptoWalletsState.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun containerOperationIsVisible() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.processCryptoWorkState.collectLatest {
                    if (it) {
                        binding.containerInStartOperation.invisible()
                        binding.containerInOperation.visible()
                    } else {
                        binding.containerInStartOperation.visible()
                        binding.containerInOperation.invisible()
                    }
                }
            }
        }
    }

    private fun updateAdapterScroll() {
        if (adapter.itemCount > 0) {
            binding.rvCryptoOperationHome.scrollToPosition(adapter.itemCount - 1)
        }
    }

}