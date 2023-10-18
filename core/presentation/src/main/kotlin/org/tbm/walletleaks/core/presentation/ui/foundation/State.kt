package org.tbm.walletleaks.core.presentation.ui.foundation

abstract class State<T> {
    var state: UIState<T> = UIState.Idle()
    var error: String? = null
}