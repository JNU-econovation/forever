package com.fourever.forever

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ForeverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

