package com.timberta.walletleaks.data.remote.interceptors

import com.timberta.walletleaks.data.local.preferences.UserDataPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val userDataPreferencesManager: UserDataPreferencesManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "bearer ${userDataPreferencesManager.accessToken}")
            .build()
        return chain.proceed(request)
    }
}