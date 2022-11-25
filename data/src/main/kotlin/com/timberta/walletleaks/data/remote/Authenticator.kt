package com.timberta.walletleaks.data.remote

import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import com.timberta.walletleaks.data.remote.apiservices.RefreshAccessTokenApiService
import com.timberta.walletleaks.data.remote.dtos.RefreshTokenDto
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class Authenticator(
    private val refreshAccessTokenApiService: RefreshAccessTokenApiService,
    private val userDataPreferencesManager: UserDataPreferencesManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            val tokenResponse = getRefreshedToken()?.execute()
            return when {
                tokenResponse?.isSuccessful == true -> {
                    userDataPreferencesManager.accessToken = tokenResponse.body()?.accessToken
                    response.request.newBuilder()
                        .addHeader("Authorization", "bearer ${tokenResponse.body()}")
                        .build()
                }
                tokenResponse?.code() == 401 -> {
                    getRefreshedToken()
                    null
                }
                tokenResponse?.code() == 400 -> null
                else -> null
            }
        }
    }

    private fun getRefreshedToken() =
        userDataPreferencesManager.refreshToken?.let {
            refreshAccessTokenApiService.refreshAccessToken(
                RefreshTokenDto(it)
            )
        }
}