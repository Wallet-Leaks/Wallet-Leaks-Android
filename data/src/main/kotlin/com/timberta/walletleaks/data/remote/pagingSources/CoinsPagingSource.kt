package com.timberta.walletleaks.data.remote.pagingSources

import android.util.Log
import com.timberta.walletleaks.data.base.BasePagingSource
import com.timberta.walletleaks.data.remote.apiservices.CoinApiService
import com.timberta.walletleaks.data.remote.dtos.CoinDto
import com.timberta.walletleaks.domain.models.CoinModel
import okio.ArrayIndexOutOfBoundsException
import java.util.*

class CoinsPagingSource(private val coinApiService: CoinApiService) :
    BasePagingSource<CoinDto, CoinModel>({
        coinApiService.fetchCoins(it).also { response ->
            response.results.apply {
                try {
                    Collections.swap(
                        this,
                        0,
                        indexOf(find { coin -> coin.symbol == "BTC" })
                    )
                    Collections.swap(
                        this,
                        1,
                        indexOf(find { coin -> coin.symbol == "ETH" })
                    )
                    Collections.swap(
                        this,
                        2,
                        indexOf(find { coin -> coin.symbol == "BNB" })
                    )
                    Collections.swap(
                        this,
                        3,
                        indexOf(find { coin -> coin.symbol == "LTC" })
                    )
                } catch (e: ArrayIndexOutOfBoundsException) {
                    Log.e("gaypop", e.localizedMessage?.toString() ?: "")
                }
            }
        }
    })