package com.test.weather.ui.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        private val TAG = "BaseApplication"

        fun getInstance(): BaseApplication? {
            return getInstance()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }


}

