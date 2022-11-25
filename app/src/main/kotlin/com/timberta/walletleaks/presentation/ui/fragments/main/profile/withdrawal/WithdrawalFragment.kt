package com.timberta.walletleaks.presentation.ui.fragments.main.profile.withdrawal

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
import com.timberta.walletleaks.presentation.extensions.bindToUIStateLoading
import com.timberta.walletleaks.presentation.extensions.directionsSafeNavigation
import com.timberta.walletleaks.presentation.extensions.invisible
import com.timberta.walletleaks.presentation.models.BalanceUI
import com.timberta.walletleaks.presentation.ui.adapters.CryptocurrencyToWithdrawAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WithdrawalFragment :
    BaseFragment<FragmentWithdrawalBinding, WithdrawalViewModel>(R.layout.fragment_withdrawal) {
    override val binding by viewBinding(FragmentWithdrawalBinding::bind)
    override val viewModel by viewModel<WithdrawalViewModel>()
    private var isCurrentDigitDirty = false
    private var isUserDeletingCardNumberDigits = false
    private val cryptocurrencyToWithdrawAdapter = CryptocurrencyToWithdrawAdapter(this::onItemClick)
    private var selectedCryptocurrencyUsdPrice = 0.0
    private val balloon by lazy {
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
    private val currentUserBalance = arrayListOf<BalanceUI>()

    override fun initialize() {
        constructRecycler()
    }

    private fun constructRecycler() {
        binding.rvCryptocurrencyToWithdraw.adapter = cryptocurrencyToWithdrawAdapter
        binding.rvCryptocurrencyToWithdraw.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun assembleViews() {
        setCardNumberItalicHint()
    }

    private fun setCardNumberItalicHint() {
        binding.etCardNumber.hint = SpannableStringBuilder().italic {
            append("Card number...")
        }
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
                isUserDeletingCardNumberDigits = before != 0
                s.apply {
                    when {
                        startsWith("4") || matches(visa) -> imCardProcessingNetwork.setImageResource(
                            R.drawable.ic_visa
                        )
                        startsWith("2") || startsWith("5") || matches(masterCard)
                        -> imCardProcessingNetwork.setImageResource(
                            R.drawable.ic_master_card
                        )
                        else -> imCardProcessingNetwork.setImageResource(R.drawable.ic_undefined_card)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) = with(StringBuilder(s.toString())) {
                if (isCurrentDigitDirty) return
                isCurrentDigitDirty = true
                s.toString().apply {
                    if (isNotEmpty() && length.rem(4 + 1) == 0) {
                        when (isUserDeletingCardNumberDigits) {
                            true -> deleteCharAt(length - 1)
                            false -> insert(length - 1, ' ')
                        }
                    }
                }
                etCardNumber.setText(this)
                etCardNumber.setSelection(this.length)
                isCurrentDigitDirty = false
            }
        })
    }

    private fun convertCryptocurrencyToUsdAndRestrictUserFromInputtingMoreThanOneDecimalSeparator() =
        with(binding) {
            etCryptocurrencyAmountToWithdrawConvertedToUsd.addTextChangedListenerAnonymously(
                doSomethingAfterTextChanged = {
                    when (it.contains('.')) {
                        true -> etCryptocurrencyAmountToWithdrawConvertedToUsd.keyListener =
                            DigitsKeyListener.getInstance("0123456789")
                        false ->
                            etCryptocurrencyAmountToWithdrawConvertedToUsd.keyListener =
                                DigitsKeyListener.getInstance("0123456789.")
                    }
                    when (it.toString().isNotEmpty()) {
                        true ->
                            tvConvertedCryptocurrencyInUsd.text =
                                (it.toString()
                                    .toDouble() * selectedCryptocurrencyUsdPrice).toString()
                        false -> tvConvertedCryptocurrencyInUsd.text = "0.0"
                    }
                    when (tvConvertedCryptocurrencyInUsd.text.toString().toDouble() < 100.00) {
                        true -> binding.imAlertCircle.showAlignBottom(balloon)
                        false -> balloon.dismiss()
                    }
                })
        }

    private fun selectCryptocurrencyToWithdraw() {
        binding.tvSelectedCryptocurrencyToWithdraw.setOnClickListener {
            binding.rvCryptocurrencyToWithdraw.isVisible =
                !binding.rvCryptocurrencyToWithdraw.isVisible
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideRecyclerViewOnClickOutside() {
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                if (binding.rvCryptocurrencyToWithdraw.isVisible) binding.rvCryptocurrencyToWithdraw.invisible()
            true
        }
    }

    private fun proceedToWithdrawalConfirmation() {
        binding.btnSubmit.setOnClickListener {
            findNavController().directionsSafeNavigation(
                WithdrawalFragmentDirections.actionWithdrawalFragmentToWithdrawalConfirmationDialogFragment(
                    binding.etCardNumber.text.toString(),
                    binding.etCryptocurrencyAmountToWithdrawConvertedToUsd.text.toString()
                )
            )
        }
    }

    override fun launchObservers() {
        subscribeToCurrentUser()
        subscribeToCryptocurrencyToWithdraw()
    }

    private fun subscribeToCurrentUser() {
        viewModel.userState.spectateUiState(success = {
            currentUserBalance.addAll(it.balance)
        })
    }

    private fun subscribeToCryptocurrencyToWithdraw() {
        viewModel.certainCoinsState.spectateUiState(success = {
            val sortedListByCoinId = it.sortedBy { unsortedList -> unsortedList.id }
            sortedListByCoinId.apply {
                cryptocurrencyToWithdrawAdapter.submitList(this)
                binding.tvSelectedCryptocurrencyToWithdraw.text = first().symbol
                selectedCryptocurrencyUsdPrice =
                    first().price?.replace(",", "")?.substringAfter("$").toString()
                        .toDouble()
                currentUserBalance.forEach { balance ->
                    if (first().id == balance.id)
                        binding.etCryptocurrencyAmountAvailableToWithdraw.setText(balance.balance.toString())
                }
            }
        }, gatherIfSucceed = {
            binding.cpiCryptocurrencyToWithdraw.bindToUIStateLoading(it)
        })
    }

    private fun onItemClick(id: Int, symbol: String, price: String) {
        binding.tvSelectedCryptocurrencyToWithdraw.text = symbol
        selectedCryptocurrencyUsdPrice = price.toDouble()
        binding.rvCryptocurrencyToWithdraw.invisible()
        currentUserBalance.forEach {
            when (id == it.id) {
                true ->
                    binding.etCryptocurrencyAmountAvailableToWithdraw.setText(it.balance.toString())
                false -> binding.etCryptocurrencyAmountAvailableToWithdraw.text?.clear()
            }
        }
    }
}