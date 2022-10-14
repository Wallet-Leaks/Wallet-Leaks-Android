package com.timberta.walletleaks

import android.app.Application
import com.timberta.walletleaks.data.di.dataModule
import com.timberta.walletleaks.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, domainModule)
        }
    }
}