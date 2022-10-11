package com.timberta.walletleaks.presentation.ui.fragments.authentication

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.data.db.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.databinding.FragmentSignInBinding
import com.timberta.walletleaks.presentation.base.BaseFragment
import com.timberta.walletleaks.presentation.extensions.navigateSafely
import org.koin.android.ext.android.inject


class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<SignInViewModel>()
    private val userDataPreferencesManager: UserDataPreferencesManager by inject()


    override fun assembleViews() {
        binding.etSecretKey.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable) {
                if (text.length == 16) {
                    binding.btnSignIn.setBackgroundColor(Color.parseColor("#37AA3E"))
                    binding.btnSignIn.setOnClickListener {
                        if (viewModel.passwordList.contains(text.toString().trim())) {
                            userDataPreferencesManager.isAuthenticated = true
                            findNavController().navigateSafely(R.id.action_signInFragment_to_mainFlowFragment)
                        }
                    }
                } else {
                    binding.btnSignIn.setBackgroundColor(Color.parseColor("#BBAABB"))
                }
            }
        })
    }

    override fun constructListeners() {

    }

}