package com.timberta.walletleaks.presentation.extensions

import android.widget.RadioButton

fun RadioButton.setOnCheckedChangeListenerAndRetrieveItsText(
    actionWhenChecked: (radioButtonText: String) -> Unit
) {
    setOnCheckedChangeListener { _, checked ->
        if (checked) {
            actionWhenChecked(text.toString())
        }
    }
}