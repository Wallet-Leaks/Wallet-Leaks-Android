package org.tbm.walletleaks.core.presentation.ui.foundation

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

abstract class ViewModel<State : Any, Action : Any, SideEffect : Any>(initialState: State) :
    ContainerHost<State, SideEffect>,
    ViewModel() {

    override val container = container<State, SideEffect>(initialState = initialState)

    open fun processAction(action: Action) {}

    fun postSideEffect(
        beforeSideEffect: SimpleSyntax<State, SideEffect>.() -> Unit = {},
        sideEffect: SideEffect,
        afterSideEffect: SimpleSyntax<State, SideEffect>.() -> Unit = {}
    ) = intent {
        beforeSideEffect()
        postSideEffect(sideEffect)
        afterSideEffect()
    }
}