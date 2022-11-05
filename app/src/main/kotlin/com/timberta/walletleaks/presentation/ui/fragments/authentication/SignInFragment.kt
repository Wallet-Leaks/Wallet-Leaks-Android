package com.timberta.walletleaks.presentation.ui.fragments.authentication

import android.util.Log
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSignInBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.addTextChangedListenerAnonymously
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModel<SignInViewModel>()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()
    private var username = false
    private var password = false
    private var confirmPassword = false

    override fun assembleViews() {
        binding.etUsername.addTextChangedListenerAnonymously(doSomethingAfterTextChanged = {
            if (it.length < 2) {
                username = false
                binding.tfUsername.error = "Username must contain at least 2 characters."
            } else {
                binding.tfUsername.error = null
                username = true
            }
        })
        binding.etPassword.addTextChangedListenerAnonymously(doSomethingAfterTextChanged = {
            if (it.length < 8) {
                password = false
                binding.tfPassword.error = "Password must contain at least 8 characters."
            } else {
                binding.tfPassword.error = null
                password = true
            }
        })
        binding.etConfirmPassword.addTextChangedListenerAnonymously(doSomethingAfterTextChanged = {
            if (binding.etPassword.text.toString() != it.toString()) {
                confirmPassword = false
                binding.tfConfirmPassword.error = "Passwords don't match."
            } else {
                binding.tfConfirmPassword.error = null
                confirmPassword = true
            }
        })
    }

    override fun constructListeners() {
        binding.btnCreateAccount.setOnClickListener {
            if (username && password && confirmPassword) {
                viewModel.signUp(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString()
                )
            }
        }
    }

    override fun launchObservers() {
        subscribeToSignUp()
    }

    private fun subscribeToSignUp() {
        viewModel.signUpState.spectateUiState(success = {
            userDataPreferencesManager.isAuthenticated = true
            findNavController().navigateSafely(R.id.action_signInFragment_to_mainFlowFragment)
        }, error = {
            Log.e("gaypop", it)
        })
    }
}