package com.timberta.walletleaks.presentation.ui.fragments.main.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentHomeBinding
import com.timberta.walletleaks.databinding.ItemWalletMiningTimeBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.*
import com.timberta.walletleaks.presentation.ui.adapters.CryptoAlgorithmAdapter
import com.timberta.walletleaks.presentation.ui.adapters.SelectedCoinsAdapter
import com.timberta.walletleaks.presentation.ui.adapters.WalletMiningTimeAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModel<HomeViewModel>()
    private val args by navArgs<HomeFragmentArgs>()
    private val cryptoAlgorithmAdapter = CryptoAlgorithmAdapter()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private val walletMiningTimeAdapter =
        WalletMiningTimeAdapter(userDataPreferencesManager.doesUserHavePremium, this::onItemClick)
    private val selectedCoinsAdapter = SelectedCoinsAdapter()
    private var selectedTime: Long = 0

    override fun initialize() {
        viewModel.processCryptoWorkState.value = false
        constructCryptoOperationHomeAdapter()
        constructWalletMiningTimeAdapter()
        constructSelectedCoinsAdapter()
        fillAdapterIfUserHasSelectedCoins()
    }

    private fun constructCryptoOperationHomeAdapter() {
        binding.rvCryptoAlgorithm.adapter = cryptoAlgorithmAdapter
        binding.rvCryptoAlgorithm.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun constructWalletMiningTimeAdapter() {
        binding.rvSelectedTime.adapter = walletMiningTimeAdapter
        binding.rvSelectedTime.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSelectedTime.itemAnimator = null
    }

    private fun constructSelectedCoinsAdapter() {
        binding.rvSelectedCoins.adapter = selectedCoinsAdapter
        binding.rvSelectedCoins.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun fillAdapterIfUserHasSelectedCoins() = with(binding) {
        args.selectedCoins?.let {
            viewModel.coinsSelectionState.value = true
            btnSaveCoins.isEnabled = true
            imAddCoins.isGone = true
            imReselectCoins.isGone = false
            btnSaveCoins.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.safetyOrange
                )
            )
            selectedCoinsAdapter.submitList(it.toList())
        }
    }

    override fun assembleViews() {
        renderCoinSelectionVisibility()
        bringAddCoinsToFront()
        retrieveLastSelectedTime()
    }

    private fun renderCoinSelectionVisibility() {
        viewModel.coinsSelectionState.value = wasCoinsSelectionValueTrue
        getBackStackData<Boolean>("coinSelection") {
            viewModel.coinsSelectionState.value = it
            findNavController().clearBackStack(R.id.homeFragment)
        }
    }

    private fun bringAddCoinsToFront() {
        binding.imAddCoins.bringToFront()
    }

    private fun retrieveLastSelectedTime() {
        getBinding(
            R.layout.item_wallet_mining_time, ItemWalletMiningTimeBinding::bind
        ).imSelectedTime.isSelected = lastSelectedPosition > 0
        walletMiningTimeAdapter.notifyItemChanged(lastSelectedPosition)
    }

    override fun constructListeners() {
        startWalletMining()
        stopWalletMining()
        checkCoinsToWalletMine()
        selectCoinsToWalletMine()
        reselectCoinsToWalletMine()
        saveCoinsToWalletMine()
    }

    private fun selectCoinsToWalletMine() {
        binding.imAddCoins.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_selectCoinsFragment)
        }
    }

    private fun reselectCoinsToWalletMine() {
        binding.imReselectCoins.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_selectCoinsFragment)
        }
    }

    private fun startWalletMining() {
        val cashTime = userDataPreferencesManager.miningTimeTimer
        binding.btnStartOperation.setOnClickListener {
            viewModel.coinsSelectionState.value = !viewModel.processCryptoWorkState.value
//            if (cashTime.toInt() == 0 || cashTime + selectedTime < System.currentTimeMillis()) {
            if (args.selectedCoins?.isNotEmpty() == true && selectedTime > 0) {
                viewModel.processCryptoWorkState.value = true
                viewModel.coinsSelectionState.value = !viewModel.processCryptoWorkState.value
                safeFlowGather {
                    userDataPreferencesManager.miningTimeTimer = System.currentTimeMillis()
                    for (i in viewModel.processIndex..10000) {
                        if (viewModel.processCryptoWorkState.value) {
                            delay(500)
                            args.selectedCoins?.let {
                                viewModel.searchCryptoWallets(it)
                            }
                            viewModel.processIndex = cryptoAlgorithmAdapter.itemCount
                            cryptoAlgorithmAdapter.notifyItemInserted(viewModel.processIndex)
                            updateAdapterScroll()
                        } else {
                            break
                        }
                    }
                }
            }
//            }
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

    private fun containerOperationIsVisible() {
        safeFlowGather {
            viewModel.processCryptoWorkState.collectLatest {
                if (it) {
                    binding.tvClickStartOnStart.gone()
                    binding.containerInStartOperation.invisible()
                    binding.containerInOperation.visible()
                    viewModel.startTimer(selectedTime)
                } else {
                    if (cryptoAlgorithmAdapter.itemCount <= 0) {
                        binding.tvClickStartOnStart.visible()
                    }
                    binding.containerInStartOperation.visible()
                    binding.containerInOperation.invisible()
                    viewModel.pauseTimer()
                }
            }
        }
    }

    private fun stopWalletMining() {
        binding.btnStopSearch.setOnClickListener {
            safeFlowGather {
                viewModel.processCryptoWorkState.value = false
                binding.btnStartOperation.isEnabled = false
                binding.btnStartOperation.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn_start_inactive)
                delay(1500)
                binding.btnStartOperation.isEnabled = true
                binding.btnStartOperation.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn_start)
            }
        }
    }

    private fun checkCoinsToWalletMine() {
        binding.btnFilterCoin.setOnClickListener {
            viewModel.coinsSelectionState.value = !viewModel.coinsSelectionState.value
        }
    }

    private fun saveCoinsToWalletMine() {
        binding.btnSaveCoins.setOnClickListener {
            viewModel.coinsSelectionState.value = false
        }
    }

    private fun spectateAndUpdateTimerCrypto() {
        safeFlowGather {
            viewModel.getTimeTimerText.collectLatest {
                binding.btnTimerOperation.text = it
            }
        }
    }

    private fun collectCryptoAlgorithm() {
        safeFlowGather {
            viewModel.getListCryptoWalletsState.collect {
                cryptoAlgorithmAdapter.submitList(it)
            }
        }
    }

    private fun updateAdapterScroll() {
        if (cryptoAlgorithmAdapter.itemCount > 0) {
            binding.rvCryptoAlgorithm.scrollToPosition(cryptoAlgorithmAdapter.itemCount - 1)
        }
    }

    private fun onItemClick(amountOfHours: Long, isUnlocked: Boolean, position: Int) {
        lastSelectedPosition = position
        selectedTime = amountOfHours
        if (!isUnlocked) findNavController().navigate(
            R.id.premiumPurchaseDialog
        )
    }

    override fun onPause() {
        super.onPause()
        wasCoinsSelectionValueTrue = binding.mcvCoinSelection.isVisible
    }

    override fun onStop() {
        super.onStop()
        viewModel.processCryptoWorkState.value = false
        wasCoinsSelectionValueTrue = binding.mcvCoinSelection.isVisible
    }

    companion object {
        var lastSelectedPosition = -1
        var wasCoinsSelectionValueTrue = false
    }
}