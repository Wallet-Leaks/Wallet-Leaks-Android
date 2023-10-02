package org.tbm.walletleaks.core.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.tbm.walletleaks.core.data.BuildConfig
import org.tbm.walletleaks.core.data.local.preferences.UserPreferences
import javax.inject.Named
import javax.inject.Singleton

@Module
object RemoteModule {

    @Named("no authentication")
    @Singleton
    @Provides
    fun provideHttpClientWithoutAuthorization() = HttpClient(OkHttp) {

        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    @Named("authentication")
    @Singleton
    @Provides
    fun provideHttpClientWithAuthentication(userPreferences: UserPreferences) = HttpClient(OkHttp) {
        install(Logging) {
            level = LogLevel.ALL
        }

        defaultRequest {
            url(BuildConfig.DEV_BASE_URL)
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(userPreferences.accessToken, userPreferences.refreshToken)
                }
                refreshTokens {
                    BearerTokens(userPreferences.accessToken, userPreferences.refreshToken)
                }
            }
        }

        install(WebSockets) {
            maxFrameSize = Long.MAX_VALUE

            contentConverter = KotlinxWebsocketSerializationConverter(Json)
            pingInterval = 20000
        }
    }
}