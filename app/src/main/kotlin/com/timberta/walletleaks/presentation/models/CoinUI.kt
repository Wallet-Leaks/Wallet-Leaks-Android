package com.timberta.walletleaks.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.timberta.walletleaks.domain.models.CoinModel
import com.timberta.walletleaks.presentation.base.BaseDiffModel


data class CoinUI(
    override val id: Int,
    val title: String?,
    val slug: String? = "",
    val url: String? = "",
    val price: String? = "",
    val symbol: String?,
    val icon: String? = "",
    var isAvailable: Boolean = true,
    var isSelected: Boolean = false,
    val image: Int? = 0
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
            writeByte(
                when (isAvailable) {
                    true -> 1
                    false -> 0
                }.toByte()
            )
            writeByte(
                when (isSelected) {
                    true -> 1
                    false -> 0
                }.toByte()
            )
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

fun CoinModel.toUI() = CoinUI(id, title, slug, url, price, symbol, icon)