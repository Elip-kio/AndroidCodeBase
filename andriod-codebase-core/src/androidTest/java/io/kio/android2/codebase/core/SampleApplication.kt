package io.kio.android2.codebase.core

import android.app.Application
import io.kio.android.android.codebase.core.ApplicationRegistry

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationRegistry.initialize(this)
    }
}