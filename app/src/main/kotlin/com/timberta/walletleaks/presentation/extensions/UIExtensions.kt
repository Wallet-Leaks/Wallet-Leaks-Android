package com.timberta.walletleaks.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageWithGlide(url: String?) = Glide.with(this).load(url).centerCrop().into(this)