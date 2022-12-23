package com.timberta.walletleaks.presentation.extensions

import android.os.Handler
import android.os.Looper
import android.util.Log

fun loge(msg: String, value: Any? = null) = Log.e("fuck", msg + value)
fun postHandler(func: () -> Unit) =
    Handler(Looper.myLooper()!!).post { func() }

/**
 * Extension function for posting a delayed [Handler]
 * */
fun postHandler(delay: Long, func: () -> Unit) =
    Handler(Looper.myLooper()!!).postDelayed(func, delay)