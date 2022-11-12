package com.timberta.walletleaks.presentation.utils

import android.text.method.PasswordTransformationMethod
import android.view.View


class AsteriskPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View) =
        PasswordCharSequence(source)

    inner class PasswordCharSequence(private val source: CharSequence) : CharSequence {
        override val length: Int
            get() = source.length

        override fun get(index: Int) = '*'

        override fun subSequence(startIndex: Int, endIndex: Int) =
            source.subSequence(startIndex, endIndex)
    }
}