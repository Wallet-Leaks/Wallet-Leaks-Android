package com.timberta.walletleaks.presentation.ui.fragments.authentication.signUp

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSignUpBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.addTextChangedListenerAnonymously
import com.timberta.walletleaks.presentation.extensions.bindToUIStateLoading
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import com.timberta.walletleaks.presentation.extensions.renderTextColorUsingTwoColors
import com.timberta.walletleaks.presentation.utils.AsteriskPasswordTransformationMethod
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, SignUpViewModel>(R.layout.fragment_sign_up) {

    override val binding by viewBinding(FragmentSignUpBinding::bind)
    override val viewModel by viewModel<SignUpViewModel>()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private var username = false
    private var password = false
    private var confirmPassword = false

    override fun assembleViews() {
        binding.tvWellcome.renderTextColorUsingTwoColors()

        binding.etPassword.transformationMethod = AsteriskPasswordTransformationMethod()
        binding.etConfirmPassword.transformationMethod = AsteriskPasswordTransformationMethod()

        binding.etUsername.addTextChangedListenerAnonymously(doSomethingAfterTextChanged = {
            if (it.length < 2) {
                username = false
                binding.tfUsername.error = "Username must contain at least 2 characters."
            } else {
                binding.tfUsername.error = null
                username = true
            }
            redrawButtonBackgroundColor()
        })
        binding.etPassword.addTextChangedListenerAnonymously(doSomethingAfterTextChanged = {
            if (it.length < 8) {
                password = false
                binding.tfPassword.error = "Password must contain at least 8 characters."
            } else {
                binding.tfPassword.error = null
                password = true
            }
            redrawButtonBackgroundColor()
        })
        binding.etConfirmPassword.addTextChangedListenerAnonymously(doSomethingAfterTextChanged = {
            if (binding.etPassword.text.toString() != it.toString()) {
                confirmPassword = false
                binding.tfConfirmPassword.error = "Passwords don't match."
            } else {
                binding.tfConfirmPassword.error = null
                confirmPassword = true

            }
            redrawButtonBackgroundColor()
        })

        binding.tfPassword.setEndIconOnClickListener {
            binding.etPassword.apply {
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
        binding.tfConfirmPassword.setEndIconOnClickListener {
            binding.etConfirmPassword.apply {
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
    }

    override fun constructListeners() {
        binding.btnSignUp.setOnClickListener {
            if (username && password && confirmPassword) {
                viewModel.signUp(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString()
                )
            }
        }
        binding.tvLogIn.setOnClickListener {
            findNavController().navigateSafely(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun launchObservers() {
        subscribeToSignUp()
    }

    private fun subscribeToSignUp() = with(binding) {
        viewModel.signUpState.spectateUiState(success = {
            userDataPreferencesManager.isAuthenticated = true
            findNavController().navigateSafely(R.id.action_signUpFragment_to_mainFlowFragment)
        }, error = {
            Log.e("gaypop", it)
        }, gatherIfSucceed = {
            cpiSignUp.bindToUIStateLoading(it)
            when (cpiSignUp.isVisible) {
                true -> btnSignUp.text = ""
                false -> btnSignUp.text = getString(R.string.sign_up)
            }
        })
    }

    private fun redrawButtonBackgroundColor() {
        if (username && password && confirmPassword)
            binding.btnSignUp.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.safetyOrange
                )
            ) else binding.btnSignUp.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.lola
            )
        )
    }
}