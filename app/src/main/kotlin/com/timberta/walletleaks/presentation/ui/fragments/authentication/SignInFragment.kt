package com.timberta.walletleaks.presentation.ui.fragments.authentication

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timberta.walletleaks.R
import com.timberta.walletleaks.databinding.FragmentSignInBinding
import com.timberta.walletleaks.presentation.base.BaseFragment


class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<SignInViewModel>()

    override fun assembleViews() {
        binding.etSecretKey.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable) {
                if (text.length == 16) {
                    binding.btnSignIn.setBackgroundColor(Color.parseColor("#37AA3E"))
                } else {
                    binding.btnSignIn.setBackgroundColor(Color.parseColor("#BBAABB"))
                }
            }
        })
    }

    override fun constructListeners() {

    }

}