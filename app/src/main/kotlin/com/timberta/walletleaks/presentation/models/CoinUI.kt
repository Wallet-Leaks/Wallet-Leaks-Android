package com.timberta.walletleaks.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.timberta.walletleaks.domain.models.CoinModel
import com.timberta.walletleaks.presentation.base.BaseDiffModel
import com.timberta.walletleaks.presentation.extensions.toByte


data class CoinUI(
    override val id: Int = 0,
    val title: String? = "",
    val slug: String? = "",
    val url: String? = "",
    val price: String? = "",
    val symbol: String?,
    val icon: String? = "",
    val isAvailable: Boolean,
    var isSelected: Boolean = false,
) : BaseDiffModel<Int>, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeInt(id)
            writeString(title)
            writeString(slug)
            writeString(url)
            writeString(price)
            writeString(symbol)
            writeString(icon)
            writeByte(isAvailable.toByte())
            writeByte(isSelected.toByte())
        }
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CoinUI> {
        override fun createFromParcel(parcel: Parcel): CoinUI {
            return CoinUI(parcel)
        }

        override fun newArray(size: Int): Array<CoinUI?> {
            return arrayOfNulls(size)
        }
    }
}

fun CoinModel.toUI() = CoinUI(id, title, slug, url, price, symbol, icon, isAvailable)