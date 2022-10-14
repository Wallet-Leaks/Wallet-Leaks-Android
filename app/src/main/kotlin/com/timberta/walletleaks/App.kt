package com.timberta.walletleaks

import android.app.Application
import com.timberta.walletleaks.data.dataModule
import com.timberta.walletleaks.di.viewModelModule
import com.timberta.walletleaks.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, viewModelModule, domainModule)
        }
    }
}