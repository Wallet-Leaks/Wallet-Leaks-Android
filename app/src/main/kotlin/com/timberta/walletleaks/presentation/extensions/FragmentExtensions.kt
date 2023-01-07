package com.timberta.walletleaks.presentation.extensions

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.timberta.walletleaks.R
import com.timberta.walletleaks.presentation.utils.CustomTypefaceSpan

fun Fragment.showShortDurationSnackbar(text: CharSequence) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showLongDurationSnackbar(text: CharSequence) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
}

fun Fragment.checkWhetherPermissionHasBeenGrantedOrNot(
    context: Context,
    permission: String,
    activity: Activity
): Intent {
    val galleryIntent =
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    when (ContextCompat.checkSelfPermission(context, permission)) {
        -1 -> ActivityCompat.requestPermissions(
            activity, arrayOf(
                permission
            ), 0
        )
    }
    return galleryIntent
}

fun Fragment.hasPermissionCheckAndRequest(
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    permission: Array<String>,
): Boolean {
    for (per in permission) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                per
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(permission)
            return false
        }
    }
    return true
}

fun Fragment.checkForPermissionStatusAndRequestIt(
    requestPermissionLauncher: ActivityResultLauncher<String>,
    permission: String,
): Boolean {
    return when (ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED) {
        true -> true

        else -> {
            requestPermissionLauncher.launch(permission)
            false
        }
    }
}

fun Fragment.hideSoftKeyboard() {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.checkWhetherSoftKeyboardIsOpenedOrNot(): Boolean {
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isAcceptingText

}

typealias Bind<T> = (View) -> T

inline fun <reified Binding : ViewBinding> Fragment.getBinding(layoutId: Int, bind: Bind<Binding>) =
    bind(
        layoutInflater.inflate(
            layoutId,
            null
        )
    )

fun Fragment.setStatusBarColor(color: Int) {
    WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
    requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), color)

}

fun Fragment.setStatusBarLightAppearance(isAppearanceLightStatusBars: Boolean) {
    view?.let {
        WindowInsetsControllerCompat(
            requireActivity().window, it
        ).isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }

}

fun Fragment.copyTheTextToClipboard(label: String = "", textToCopy: String) =
    ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
        ?.setPrimaryClip(
            ClipData.newPlainText(
                label,
                textToCopy
            )
        )

fun Fragment.transformTextFont(
    textToTransform: String,
    @FontRes fontId: Int,
    startIndex: Int,
    endIndex: Int
): SpannableStringBuilder {
    val span = SpannableStringBuilder(textToTransform)
    val typeface =
        ResourcesCompat.getFont(requireContext(), fontId)
    span.setSpan(
        CustomTypefaceSpan(typeface),
        startIndex, endIndex,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    return span
}

fun Fragment.openTelegramBasedAppViaLink(
    link: String = getString(R.string.telegram_wallet_leaks_link),
    action: (() -> Unit)? = null
) {
    Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
        try {
            action?.invoke()
            startActivity(this)
        } catch (activityNotFoundException: ActivityNotFoundException) {
            showShortDurationSnackbar(getString(R.string.telegram_is_not_installed))
        }
    }
}

fun Fragment.buildBalloon(
    @StringRes stringId: Int,
    @ColorRes textColor: Int = R.color.blueSentinel,
    width: Int = 194,
    typeface: Typeface? = (ResourcesCompat.getFont(
        requireContext(),
        R.font.roboto_bold
    ) as Typeface),
    height: Int,
    textSize: Float = 11.5F,
    arrowPositionRules: ArrowPositionRules = ArrowPositionRules.ALIGN_ANCHOR,
    arrowSize: Int = 10,
    arrowDrawable: Drawable? = ContextCompat.getDrawable(
        requireContext(),
        R.drawable.ic_tooltip_arrow
    ),
    arrowPosition: Float,
    cornerRadius: Float = 6f,
    @ColorRes backgroundColor: Int = R.color.white,
    balloonAnimation: BalloonAnimation = BalloonAnimation.OVERSHOOT,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    dismissWhenTouchOutside: Boolean = false

) =
    Balloon.Builder(requireContext())
        .setText(getString(stringId))
        .setTextColorResource(textColor)
        .setWidth(width)
        .setTextTypeface(
            typeface as Typeface
        )
        .setHeight(height)
        .setTextSize(textSize)
        .setArrowPositionRules(arrowPositionRules)
        .setArrowSize(arrowSize)
        .setArrowDrawable(
            arrowDrawable
        )
        .setArrowPosition(arrowPosition)
        .setCornerRadius(cornerRadius)
        .setBackgroundColorResource(backgroundColor)
        .setBalloonAnimation(balloonAnimation)
        .setLifecycleOwner(lifecycleOwner)
        .setDismissWhenTouchOutside(dismissWhenTouchOutside)
        .build()