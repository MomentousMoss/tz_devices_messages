package com.momentousmoss.tz_devices_messages

import android.app.Application
import com.momentousmoss.tz_devices_messages.database.AppDatabase
import com.momentousmoss.tz_devices_messages.ui.viewModelsModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(
                viewModelsModule,
                module {
                    single { AppDatabase(applicationContext) }
                }
            )
        }
    }
}