package com.timberta.walletleaks.presentation.ui.fragments.authentication.signIn

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSignInBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.*
import com.timberta.walletleaks.presentation.utils.AsteriskPasswordTransformationMethod
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModel<SignInViewModel>()
    private var doesUsernameMeetsTheRequirements = false
    private var doesPasswordMeetsTheRequirements = false
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()

    override fun assembleViews() {
        renderAppName()
        setAsteriskTransformationMethod()
    }

    private fun renderAppName() {
        binding.tvAppTitle.renderTextColorUsingTwoColors()
    }

    private fun setAsteriskTransformationMethod() {
        binding.etPassword.transformationMethod = AsteriskPasswordTransformationMethod()
    }

    override fun constructListeners() {
        changeTextColorIfPasswordIsToggled()
        displayUsernameAndPasswordInputErrors()
        logIn()
        navigateToSignUp()
        clickOnFaq()
    }

    private fun changeTextColorIfPasswordIsToggled() = with(binding.etPassword) {
        binding.tlPassword.setEndIconOnClickListener {
            when (transformationMethod) {
                null -> {
                    transformationMethod = AsteriskPasswordTransformationMethod()
                    setSelection(binding.etPassword.text.toString().length)
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blueSentinel
                        )
                    )
                }
                else -> {
                    transformationMethod = null
                    setSelection(binding.etPassword.text.toString().length)
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                }
            }
        }
    }

    private fun displayUsernameAndPasswordInputErrors() = with(binding) {
        etUsername.addTextChangedListenerAnonymously(doSomethingOnTextChanged = {
            when (etUsername.text.toString().length >= 2) {
                false -> {
                    tlUsername.error = "Username must contain at least 2 characters."
                    doesUsernameMeetsTheRequirements = false
                }
                true -> {
                    doesUsernameMeetsTheRequirements = true
                    tlUsername.isErrorEnabled = false
                }
            }
            redrawLogInButtonBackgroundColorDependingOnTheBooleansValue()
        })
        etPassword.addTextChangedListenerAnonymously(doSomethingOnTextChanged = {
            when (etPassword.text.toString().length >= 8) {
                false -> {
                    tlPassword.error = "Password must contain at least 8 characters."
                    doesPasswordMeetsTheRequirements = false
                }
                true -> {
                    doesPasswordMeetsTheRequirements = true
                    tlPassword.isErrorEnabled = false
                }
            }
            redrawLogInButtonBackgroundColorDependingOnTheBooleansValue()
        })
    }

    private fun logIn() {
        binding.btnLogIn.setOnClickListener {
            if (doesUsernameMeetsTheRequirements && doesPasswordMeetsTheRequirements) {
                hideSoftKeyboard()
                viewModel.logIn(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
    }

    private fun navigateToSignUp() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigateSafely(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun clickOnFaq() {
        binding.tvFaq.setOnClickListener {
            openTelegramBasedAppViaLink(getString(R.string.telegram_faq_channel_link))
        }
    }

    override fun launchObservers() {
        subscribeToLogIn()
    }

    private fun subscribeToLogIn() = with(binding) {
        viewModel.logInState.spectateUiState(success = {
            userDataPreferencesManager.isAuthenticated = true
            findNavController().navigateSafely(R.id.action_signInFragment_to_mainFlowFragment)
        }, error = {
            showShortDurationSnackbar(it)
        }, gatherIfSucceed = {
            binding.cpiLogIn.bindToUIStateLoading(it)
            when (cpiLogIn.isVisible) {
                true -> btnLogIn.text = ""
                false -> btnLogIn.text = getString(R.string.log_in)
            }
        })
    }

    private fun redrawLogInButtonBackgroundColorDependingOnTheBooleansValue() {
        when (doesUsernameMeetsTheRequirements && doesPasswordMeetsTheRequirements) {
            true -> binding.btnLogIn.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.safetyOrange
                )
            )
            false -> binding.btnLogIn.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.lola
                )
            )
        }
    }
}