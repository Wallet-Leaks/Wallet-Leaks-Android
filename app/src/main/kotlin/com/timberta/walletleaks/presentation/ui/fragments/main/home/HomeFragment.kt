package com.timberta.walletleaks.presentation.ui.fragments.main.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentHomeBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.gone
import com.timberta.walletleaks.presentation.extensions.invisible
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import com.timberta.walletleaks.presentation.extensions.visible
import com.timberta.walletleaks.presentation.ui.adapters.CryptoAlgorithmAdapter
import com.timberta.walletleaks.presentation.ui.adapters.SelectedCoinsAdapter
import com.timberta.walletleaks.presentation.ui.adapters.WalletMiningTimeAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import kotlin.random.Random


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()
    private val args by navArgs<HomeFragmentArgs>()
    private val adapter = CryptoAlgorithmAdapter()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private val walletMiningTimeAdapter =
        WalletMiningTimeAdapter(userDataPreferencesManager.doesUserHavePremium, this::onItemClick)
    private val selectedCoinsAdapter = SelectedCoinsAdapter()
    private var selectedTime: Long = 0

    override fun initialize() {
        viewModel.processCryptoWorkState.value = false
        binding.rvCryptoOperationHome.adapter = adapter
        constructWalletMiningTimeAdapter()
        constructSelectedCoinsAdapter()
    }

    private fun constructWalletMiningTimeAdapter() {
        binding.rvSelectedTime.adapter = walletMiningTimeAdapter
        binding.rvSelectedTime.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSelectedTime.itemAnimator = null
    }

    private fun constructSelectedCoinsAdapter() = with(binding) {
        rvSelectedCoins.adapter = selectedCoinsAdapter
        rvSelectedCoins.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        args.selectedCoins?.let {
            viewModel.coinsSelectionState.value = true
            btnSaveCoins.isEnabled = true
            btnSaveCoins.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.safetyOrange
                )
            )
            imAddCoins.isGone = true
            imReselectCoins.isGone = false
            selectedCoinsAdapter.submitList(it.toList())
        }
    }

    override fun assembleViews() {
        bringAddCoinsToFront()
    }

    private fun bringAddCoinsToFront() {
        binding.imAddCoins.bringToFront()
    }

    override fun constructListeners() {
        startWalletMining()
        stopWalletMining()
        checkCoinsToWalletMine()
        selectCoinsToWalletMine()
        reselectCoinsToWalletMine()
        saveCoinsToWalletMine()
    }

    private fun startWalletMining() {
        val cashTime = userDataPreferencesManager.miningTimeTimer
        binding.btnStartOperation.setOnClickListener {
            viewModel.coinsSelectionState.value = !viewModel.processCryptoWorkState.value
            if (cashTime.toInt() == 0 || cashTime + 21600000 > System.currentTimeMillis() && args.selectedCoins?.isNotEmpty() == true) {
                viewModel.processCryptoWorkState.value = true
                viewModel.coinsSelectionState.value = !viewModel.processCryptoWorkState.value
                safeFlowGather {
                    userDataPreferencesManager.miningTimeTimer = System.currentTimeMillis()
                    for (i in viewModel.processIndex..10000) {
                        if (viewModel.processCryptoWorkState.value) {
                            delay(Random.nextLong(200, 700))
                            viewModel.processIndex = adapter.itemCount
                            viewModel.searchCryptoWallets()
                            adapter.notifyItemInserted(viewModel.processIndex)
                            updateAdapterScroll()
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }

    private fun stopWalletMining() {
        binding.btnStopSearch.setOnClickListener {
            viewModel.processCryptoWorkState.value = false
        }
    }

    private fun checkCoinsToWalletMine() {
        binding.icFilter.setOnClickListener {
            viewModel.coinsSelectionState.value = !viewModel.coinsSelectionState.value
        }
    }

    private fun selectCoinsToWalletMine() {
        binding.imAddCoins.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_selectCoinsFragment)
        }
    }

    private fun saveCoinsToWalletMine() {
        binding.btnSaveCoins.setOnClickListener {
            viewModel.coinsSelectionState.value = false
        }
    }

    private fun reselectCoinsToWalletMine() {
        binding.imReselectCoins.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_selectCoinsFragment)
        }
    }

    override fun launchObservers() {
        spectateCoinsSelectionProcess()
        collectCryptoAlgorithm()
        containerOperationIsVisible()
        spectateAndUpdateTimerCrypto()
    }

    private fun spectateCoinsSelectionProcess() = with(binding) {
        safeFlowGather {
            viewModel.coinsSelectionState.collectLatest {
                when (it) {
                    true -> {
                        mcvCoinSelection.visible()
                        icFilter.setImageResource(R.drawable.ic_filter_activated)
                    }
                    false -> {
                        mcvCoinSelection.gone()
                        icFilter.setImageResource(R.drawable.ic_filter_not_activated)
                    }
                }
            }
        }
    }

    private fun collectCryptoAlgorithm() {
        safeFlowGather {
            viewModel.getListCryptoWalletsState.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun containerOperationIsVisible() {
        safeFlowGather {
            viewModel.processCryptoWorkState.collectLatest {
                if (it) {
                    binding.containerInStartOperation.invisible()
                    binding.containerInOperation.visible()
                    viewModel.startTimer(selectedTime)
                } else {
                    binding.containerInStartOperation.visible()
                    binding.containerInOperation.invisible()
                    viewModel.pauseTimer()
                }
            }
        }
    }

    private fun spectateAndUpdateTimerCrypto() {
        safeFlowGather {
            viewModel.getTimeTimerText.collectLatest {
                binding.btnTimerOperation.text = it
            }
        }
    }

    private fun updateAdapterScroll() {
        if (adapter.itemCount > 0) {
            binding.rvCryptoOperationHome.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun onItemClick(amountOfHours: Long, isUnlocked: Boolean) {
        selectedTime = amountOfHours
        if (!isUnlocked) findNavController().navigate(
            R.id.premiumPurchaseFragment
        )
    }

    override fun onStop() {
        super.onStop()
        viewModel.processCryptoWorkState.value = false
    }
}