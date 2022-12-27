package com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.italic
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.balloon.*
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentWithdrawalBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.addTextChangedListenerAnonymously
import com.timberta.walletleaks.presentation.extensions.directionsSafeNavigation
import com.timberta.walletleaks.presentation.extensions.invisible
import com.timberta.walletleaks.presentation.models.BalanceUI
import com.timberta.walletleaks.presentation.models.CardProcessingNetwork
import com.timberta.walletleaks.presentation.ui.adapters.CryptocurrencyToWithdrawAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat


class WithdrawalFragment :
    BaseFragment<FragmentWithdrawalBinding, WithdrawalViewModel>(R.layout.fragment_withdrawal) {
    override val binding by viewBinding(FragmentWithdrawalBinding::bind)
    override val viewModel by viewModel<WithdrawalViewModel>()
    private var isCurrentDigitDirty = false
    private var isUserDeletingCardNumberDigits = false
    private val cryptocurrencyToWithdrawAdapter = CryptocurrencyToWithdrawAdapter(this::onItemClick)
    private var selectedCryptocurrencyId = 0
    private var selectedCryptocurrencyUsdPrice = 0.0
    private val balloonMinimumTransaction by lazy {
        Balloon.Builder(requireContext())
            .setText("The minimum transaction is at least $100!")
            .setTextColorResource(R.color.blueSentinel)
            .setWidth(194)
            .setTextTypeface(
                ResourcesCompat.getFont(
                    requireContext(),
                    R.font.roboto_bold
                ) as Typeface
            )
            .setHeight(34)
            .setTextSize(11.5F)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_tooltip_arrow
                )
            )
            .setArrowPosition(0.5f)
            .setCornerRadius(6f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            .setLifecycleOwner(viewLifecycleOwner)
            .setDismissWhenTouchOutside(false)
            .build()
    }
    private val balloonYouWantToWithdrawIsMoreThanYouCurrentlyHave by lazy {
        Balloon.Builder(requireContext())
            .setText("The cryptocurrency amount you want to \n withdraw is more than you currently own.")
            .setTextColorResource(R.color.blueSentinel)
            .setWidth(194)
            .setTextTypeface(
                ResourcesCompat.getFont(
                    requireContext(),
                    R.font.roboto_bold
                ) as Typeface
            )
            .setHeight(54)
            .setTextSize(11.5F)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_tooltip_arrow
                )
            )
            .setArrowPosition(0.115F)
            .setCornerRadius(6f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
            .setLifecycleOwner(viewLifecycleOwner)
            .setDismissWhenTouchOutside(false)
            .build()
    }
    private val currentUserBalance = arrayListOf<BalanceUI>()
    private var cardProcessingNetwork = CardProcessingNetwork.UNDEFINED
    private var hasUserInputProperCard = false
    private var isSelectedAmountOfCryptocurrencyLessOrEqualToCurrentlyAvailable = false
    private var isSelectedConvertedCryptocurrencyMoreThanHundredDollars = false

    override fun initialize() {
        constructRecycler()
    }

    private fun constructRecycler() {
        binding.rvCryptocurrencyToWithdraw.adapter = cryptocurrencyToWithdrawAdapter
        binding.rvCryptocurrencyToWithdraw.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun constructListeners() {
        navigateBack()
        renderCardProcessingNetworkAndInsertSpaceEveryFourDigits()
        selectCryptocurrencyToWithdraw()
        hideRecyclerViewOnClickOutside()
        convertCryptocurrencyToUsdAndRestrictUserFromInputtingMoreThanOneDecimalSeparator()
        proceedToWithdrawalConfirmation()
    }

    private fun navigateBack() {
        binding.mtWithdrawal.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun renderCardProcessingNetworkAndInsertSpaceEveryFourDigits() = with(binding) {
        val visa = "^4[0-9]{12}(?:[0-9]{3}){0,2}$".toRegex()
        val masterCard = "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$".toRegex()
        etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hasUserInputProperCard =
                    when (s.length == 22 && cardProcessingNetwork != CardProcessingNetwork.UNDEFINED) {
                        true -> true
                        false -> false
                    }
                isUserDeletingCardNumberDigits = before != 0
                s.apply {
                    cardProcessingNetwork = when {
                        startsWith("4") || matches(visa) -> {
                            imCardProcessingNetwork.setImageResource(
                                R.drawable.ic_visa
                            )
                            CardProcessingNetwork.VISA
                        }
                        startsWith("2") || startsWith("5") || matches(masterCard)
                        -> {
                            imCardProcessingNetwork.setImageResource(
                                R.drawable.ic_master_card
                            )
                            CardProcessingNetwork.MASTERCARD
                        }
                        else -> {
                            imCardProcessingNetwork.setImageResource(R.drawable.ic_undefined_card)
                            CardProcessingNetwork.UNDEFINED
                        }
                    }
                }
                redrawWithdrawalSubmissionButtonAccordingToUserAbilityToSubmitWithdrawal()
            }

            override fun afterTextChanged(s: Editable) = with(StringBuilder(s.toString())) {
                if (isCurrentDigitDirty) return
                isCurrentDigitDirty = true
                s.toString().apply {
                    if (isNotEmpty() && length.rem(4 + 2) == 0) {
                        when (isUserDeletingCardNumberDigits) {
                            true -> deleteCharAt(length - 2)
                            false -> insert(length - 2, "  ")
                        }
                    }
                }
                etCardNumber.setText(this)
                etCardNumber.setSelection(this.length)
                isCurrentDigitDirty = false
            }
        })
    }

    private fun selectCryptocurrencyToWithdraw() {
        binding.tvSelectedCryptocurrencyToWithdraw.setOnClickListener {
            binding.mcvCryptocurrencyToWithdraw.isVisible =
                !binding.mcvCryptocurrencyToWithdraw.isVisible
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideRecyclerViewOnClickOutside() {
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                if (binding.mcvCryptocurrencyToWithdraw.isVisible) binding.mcvCryptocurrencyToWithdraw.invisible()
            true
        }
    }

    private fun convertCryptocurrencyToUsdAndRestrictUserFromInputtingMoreThanOneDecimalSeparator() =
        with(binding) {
            etCryptocurrencyAmountToWithdrawConvertedToUsd.addTextChangedListenerAnonymously(
                doSomethingAfterTextChanged = {
                    etCryptocurrencyAmountToWithdrawConvertedToUsd.keyListener =
                        when (it.contains('.')) {
                            true ->
                                DigitsKeyListener.getInstance("0123456789")
                            false ->
                                DigitsKeyListener.getInstance("0123456789.")
                        }

                    tvConvertedCryptocurrencyInUsd.text =
                        when (it.toString().isNotEmpty() && it.matches(Regex(".*\\d.*"))) {
                            true ->
                                (it.toString()
                                    .toDouble() * selectedCryptocurrencyUsdPrice).toString()
                            false -> "0.0"
                        }
                    isSelectedConvertedCryptocurrencyMoreThanHundredDollars =
                        when (it.isNotEmpty() && tvConvertedCryptocurrencyInUsd.text.toString()
                            .toDouble() >= 100.00) {
                            true -> {
                                balloonMinimumTransaction.dismiss()
                                true
                            }
                            false -> {
                                if (!balloonYouWantToWithdrawIsMoreThanYouCurrentlyHave.isShowing)
                                    imAlertCircle.showAlignBottom(balloonMinimumTransaction)
                                false
                            }
                        }
                    isSelectedAmountOfCryptocurrencyLessOrEqualToCurrentlyAvailable =
                        when (it.isNotEmpty()
                                && it.matches(Regex(".*\\d.*")) && etCryptocurrencyAmountAvailableToWithdraw.text.toString()
                            .isNotEmpty() && it.toString()
                            .toDouble() <= etCryptocurrencyAmountAvailableToWithdraw.text.toString()
                            .toDouble()) {
                            true -> {
                                balloonYouWantToWithdrawIsMoreThanYouCurrentlyHave.dismiss()
                                true
                            }
                            false -> {
                                when (it.isNotEmpty()) {
                                    true -> {
                                        imAlertCircleAmountToWithdrawIsMoreThanCurrentlyAvailable.showAlignBottom(
                                            balloonYouWantToWithdrawIsMoreThanYouCurrentlyHave
                                        )
                                        balloonMinimumTransaction.dismiss()
                                    }
                                    false -> {
                                        balloonYouWantToWithdrawIsMoreThanYouCurrentlyHave.dismiss()
                                    }
                                }
                                balloonMinimumTransaction.dismiss()
                                false
                            }
                        }
                    redrawWithdrawalSubmissionButtonAccordingToUserAbilityToSubmitWithdrawal()
                })
        }

    private fun proceedToWithdrawalConfirmation() = with(binding) {
        btnSubmit.setOnClickListener {
            findNavController().directionsSafeNavigation(
                WithdrawalFragmentDirections.actionWithdrawalFragmentToWithdrawalConfirmationDialogFragment(
                    cardProcessingNetwork,
                    etCardNumber.text.toString().replace("  ", "")
                        .replace(Regex("""^(?:\D*\d){12}""")) {
                            it.value.replace(
                                Regex("""\d"""),
                                "*"
                            )
                        },
                    tvConvertedCryptocurrencyInUsd.text.toString(),
                    (etCryptocurrencyAmountAvailableToWithdraw.text.toString()
                        .toDouble() - etCryptocurrencyAmountToWithdrawConvertedToUsd.text.toString()
                        .toDouble()).toString(),
                    selectedCryptocurrencyId
                )
            )
        }
    }

    override fun launchObservers() {
        subscribeToCurrentUser()
        subscribeToCryptocurrencyToWithdraw()
        subscribeToOverallLoadingState()
    }

    private fun subscribeToCurrentUser() = with(binding) {
        viewModel.userState.spectateUiState(success = { user ->
            viewModel.modifyLoadingState()
            if (!sflWithdrawal.isShimmerVisible) {
                tvUsername.text = user.username
                tvCurrentUserBalance.setBackgroundResource(R.drawable.balance_radial_background)
                ValueAnimator.ofFloat(
                    0.0f,
                    DecimalFormat("#.##").format(user.totalBalance.toDouble()).toString().replace(".", ",")
                        .toFloat()
                ).apply {
                    duration = 2500
                    addUpdateListener { animation ->
                        tvCurrentUserBalance.text = getString(
                            R.string.money_with_dollar_sign,
                            animation.animatedValue.toString()
                        )
                    }
                    start()
                }
                etCryptocurrencyAmountAvailableToWithdraw.setText(
                    currentUserBalance.find { balance -> balance.coin.symbol == "BTC" }?.balance.toString()
                )
            }
            currentUserBalance.addAll(user.balance)
        })
    }

    private fun subscribeToCryptocurrencyToWithdraw() {
        viewModel.certainCoinsState.spectateUiState(success = {
            viewModel.modifyLoadingState()
            val sortedListByCoinId = it.sortedBy { unsortedList -> unsortedList.id }
            sortedListByCoinId.apply {
                selectedCryptocurrencyId = first().id
                cryptocurrencyToWithdrawAdapter.submitList(this)
                if (!binding.sflWithdrawal.isShimmerVisible)
                    binding.tvSelectedCryptocurrencyToWithdraw.text = first().symbol
                selectedCryptocurrencyUsdPrice =
                    first().price?.replace(",", "")?.substringAfter("$").toString()
                        .toDouble()
                currentUserBalance.forEach { balance ->
                    if (first().id == balance.id)
                        binding.etCryptocurrencyAmountAvailableToWithdraw.setText(balance.balance.toString())
                }
            }
        })
    }

    private fun subscribeToOverallLoadingState() = with(binding.sflWithdrawal) {
        binding.apply {
            safeFlowGather {
                viewModel.overallLoadingState.collectLatest {
                    when (it) {
                        in 0..1 ->
                            startShimmer()
                        2 -> {
                            stopShimmer()
                            hideShimmer()
                            redrawViewsWhenShimmerIsInvisible()
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(id: Int, symbol: String, price: String) = with(binding) {
        tvSelectedCryptocurrencyToWithdraw.text = symbol
        selectedCryptocurrencyUsdPrice = price.toDouble()
        selectedCryptocurrencyId = id
        mcvCryptocurrencyToWithdraw.invisible()
        binding.etCryptocurrencyAmountAvailableToWithdraw.setText(
            currentUserBalance.find { balance -> symbol == balance.coin.symbol }?.balance.toString()
        )
        when (etCryptocurrencyAmountToWithdrawConvertedToUsd.text.toString().isNotEmpty()) {
            true ->
                tvConvertedCryptocurrencyInUsd.text =
                    (etCryptocurrencyAmountToWithdrawConvertedToUsd.text.toString()
                        .toDouble() * selectedCryptocurrencyUsdPrice).toString()
            false -> tvConvertedCryptocurrencyInUsd.text = "0.0"
        }
    }

    private fun redrawWithdrawalSubmissionButtonAccordingToUserAbilityToSubmitWithdrawal() {
        binding.btnSubmit.isEnabled =
            when (hasUserInputProperCard && isSelectedConvertedCryptocurrencyMoreThanHundredDollars && isSelectedAmountOfCryptocurrencyLessOrEqualToCurrentlyAvailable) {
                true -> {
                    binding.btnSubmit.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.safetyOrange
                        )
                    )
                    true
                }
                false -> {
                    binding.btnSubmit.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lola
                        )
                    )
                    false
                }
            }
    }

    private fun redrawViewsWhenShimmerIsInvisible() = with(binding) {
        imCardProcessingNetwork.setImageResource(R.drawable.ic_undefined_card)
        imAlertCircle.setImageResource(R.drawable.ic_alert_circle)
        imAlertCircleAmountToWithdrawIsMoreThanCurrentlyAvailable.setImageResource(
            R.drawable.ic_alert_circle
        )
        imEquals.setImageResource(R.drawable.ic_equals)
        etCardNumber.hint = SpannableStringBuilder().italic {
            append("Card number...")
        }
        etCardNumber.isEnabled = true
        imUsernameShimmer.invisible()
        imCardWithdrawalShimmer.invisible()
        btnSubmit.text = getString(R.string.submit)
        redrawWithdrawalSubmissionButtonAccordingToUserAbilityToSubmitWithdrawal()
        etCryptocurrencyAmountAvailableToWithdraw.hint = "0.000000"
        etCryptocurrencyAmountToWithdrawConvertedToUsd.isEnabled = true
        tvSelectedCryptocurrencyToWithdraw.isEnabled = true
        subscribeToCurrentUser()
        subscribeToCryptocurrencyToWithdraw()
    }
}