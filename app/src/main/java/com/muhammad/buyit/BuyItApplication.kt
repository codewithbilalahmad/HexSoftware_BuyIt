package com.muhammad.buyit

import android.app.Application
import com.muhammad.buyit.di.appModule
import com.muhammad.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BuyItApplication : Application(){
    companion object{
        lateinit var INSTANCE : BuyItApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@BuyItApplication)
            androidLogger()
            modules(appModule, dataModule)
        }
    }
}