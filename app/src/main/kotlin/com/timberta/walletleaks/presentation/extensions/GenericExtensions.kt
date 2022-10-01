package com.timberta.walletleaks.presentation.extensions

inline fun <reified T : Any> T.className(): String = this::class.java.simpleName