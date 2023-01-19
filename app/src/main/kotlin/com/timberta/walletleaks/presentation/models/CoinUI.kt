package com.timberta.walletleaks.presentation.models

import android.os.Parcelable
import com.timberta.walletleaks.domain.models.CoinModel
import com.timberta.walletleaks.presentation.base.BaseDiffModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinUI(
    override val id: Int = 0,
    val title: String? = "",
    val slug: String? = "",
    val url: String? = "",
    val price: String? = "",
    val symbol: String?,
    val icon: String? = "",
    val isAvailable: Boolean,
) : BaseDiffModel<Int>, Parcelable

fun CoinModel.toUI() = CoinUI(id, title, slug, url, price, symbol, icon, isAvailable)