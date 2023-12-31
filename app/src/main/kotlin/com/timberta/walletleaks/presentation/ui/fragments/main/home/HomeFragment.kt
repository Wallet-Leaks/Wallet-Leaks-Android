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
    private var walletMiningTimeAdapter =
        WalletMiningTimeAdapter(
            userDataPreferencesManager.doesUserHavePremium,
            this::onItemClick,
            userDataPreferencesManager.selectedTimeToMine,
            userDataPreferencesManager.miningAvailability
        )
    private val selectedCoinsAdapter = SelectedCoinsAdapter()
    private var selectedTime: Long = 0

    override fun initialize() {
        initMiningAvailability()
        viewModel.processCryptoWorkState.value = false
        constructCryptoOperationHomeAdapter()
        constructWalletMiningTimeAdapter()
        constructSelectedCoinsAdapter()
        fillAdapterIfUserHasSelectedCoins()
    }

    private fun initMiningAvailability() {
        val miningAvailabilityTime = userDataPreferencesManager.timeLeftToMine
        if (miningAvailabilityTime.toInt() == 0 || miningAvailabilityTime + 24 * 60 * 60 * 1000 <= System.currentTimeMillis()) {
            userDataPreferencesManager.miningAvailability = false
        }
        walletMiningTimeAdapter.modifyMining(
            userDataPreferencesManager.selectedTimeToMine,
            userDataPreferencesManager.miningAvailability
        )
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
        ).imSelectedTime.isSelected = userDataPreferencesManager.lastSelectedPosition > 0
        walletMiningTimeAdapter.notifyItemChanged(userDataPreferencesManager.lastSelectedPosition)
    }

    override fun constructListeners() {
        clickStartWalletMining()
        binding.btnStopSearch.setOnClickListener {
            stopWalletMining()
        }
        checkCoinsToWalletMine()
        selectCoinsToWalletMine()
        saveCoinsToWalletMine()
    }

    private fun selectCoinsToWalletMine() {
        binding.imAddCoins.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_selectCoinsFragment)
        }
        binding.imReselectCoins.setOnClickListener {
            findNavController().navigateSafely(R.id.action_homeFragment_to_selectCoinsFragment)
        }
    }

    private fun clickStartWalletMining() {
        binding.btnStartOperation.setOnClickListener {
            observeNetworkConnectionStatusAndAction(actionWhenConnected = {
                val miningAvailabilityTime = userDataPreferencesManager.timeLeftToMine
                viewModel.coinsSelectionState.value = !viewModel.processCryptoWorkState.value
                if (args.selectedCoins?.isNotEmpty() == true) {
                    if (miningAvailabilityTime + 24 * 60 * 60 * 1000 <= System.currentTimeMillis() || userDataPreferencesManager.selectedTimeToMine > 0) {
                        startMining()
                    } else {
                        findNavController().navigate(R.id.attemptMiningDialog)
                    }
                }
            }, actionWhenDisconnected = {
                showCustomToast(getString(R.string.wallet_mining_requires_stable_internet_connection))
            })
        }
    }

    private fun startMining() {
        if (selectedTime > 0) {
            userDataPreferencesManager.selectedTimeToMine = selectedTime
            userDataPreferencesManager.miningAvailability = true
            selectedTime = 0
        }
        if (userDataPreferencesManager.selectedTimeToMine > 0) {
            userDataPreferencesManager.timeLeftToMine = System.currentTimeMillis()
            viewModel.processCryptoWorkState.value = true
            viewModel.coinsSelectionState.value =
                !viewModel.processCryptoWorkState.value
            initMiningAvailability()
            safeFlowGather {
                for (i in viewModel.processIndex..10000) {
                    if (viewModel.processCryptoWorkState.value && userDataPreferencesManager.selectedTimeToMine > 0) {
                        args.selectedCoins?.forEach {
                            delay(500)
                            viewModel.searchCryptoWallets(it)
                            viewModel.processIndex =
                                cryptoAlgorithmAdapter.itemCount
                            cryptoAlgorithmAdapter.notifyItemInserted(viewModel.processIndex)
                            updateAdapterScroll()
                        }
                    } else {
                        stopWalletMining()
                        break
                    }
                }
            }
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
                    if (userDataPreferencesManager.selectedTimeToMine.toInt() > 0) {
                        viewModel.startTimer(userDataPreferencesManager.selectedTimeToMine)
                    } else {
                        viewModel.startTimer(selectedTime)
                        userDataPreferencesManager.selectedTimeToMine = selectedTime
                    }
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

    private fun checkCoinsToWalletMine() {
        binding.btnFilterCoin.setOnClickListener {
            walletMiningTimeAdapter =
                WalletMiningTimeAdapter(
                    userDataPreferencesManager.doesUserHavePremium,
                    this::onItemClick,
                    userDataPreferencesManager.selectedTimeToMine,
                    userDataPreferencesManager.miningAvailability
                )
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
            viewModel.getTimeTimerTextState.collectLatest {
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
        userDataPreferencesManager.lastSelectedPosition = position
        if (userDataPreferencesManager.selectedTimeToMine <= 0) {
            selectedTime = amountOfHours
        }
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
        var wasCoinsSelectionValueTrue = false
    }
}