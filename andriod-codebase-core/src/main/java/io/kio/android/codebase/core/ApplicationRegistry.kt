package io.kio.android.codebase.core

import android.app.Application

class ApplicationRegistry<T : Application> private constructor(private val app: T) {

    val application: T
        get() = app

    val globalApplicationContext
        get() = app.applicationContext!!

    companion object {

        @Volatile
        private var instance: ApplicationRegistry<Application>? = null

        fun initialize(application: Application) {
            if (instance != null) throw UnsupportedOperationException("ApplicationRegistry has been already initialized!")
            synchronized(ApplicationRegistry::class.java) {
                instance = ApplicationRegistry(application)
                PluginRegistry.notifyPlugins()
            }
        }

        fun <T : Application> getInstance(): ApplicationRegistry<T> {
            if (instance == null) throw IllegalStateException("ApplicationRegistry has not been initialized yet!")

            @Suppress("UNCHECKED_CAST")
            return instance as ApplicationRegistry<T>
        }
    }
}