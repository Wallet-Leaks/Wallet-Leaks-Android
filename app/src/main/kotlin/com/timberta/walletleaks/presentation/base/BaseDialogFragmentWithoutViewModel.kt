package com.timberta.walletleaks.presentation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragmentWithoutViewModel<Binding : ViewBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatDialogFragment() {
    protected abstract val binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assembleViews()
        initialize()
        setupListeners()
    }

    protected open fun initialize() {}

    protected open fun assembleViews() {}

    protected open fun setupListeners() {}

//    protected fun setWidthPercent(percentage: Int) {
//        val percent = percentage.toFloat() / 100
//        val dm = Resources.getSystem().displayMetrics
//        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
//        val percentWidth = rect.width() * percent
//        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
//    }
}