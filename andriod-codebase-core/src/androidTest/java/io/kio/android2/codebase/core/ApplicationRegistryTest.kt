package io.kio.android2.codebase.core

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kio.android.codebase.core.ApplicationRegistry

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ApplicationRegistryTest {

    @Test
    fun initializeApplicationRegistry() {
        val registry = ApplicationRegistry.getInstance<SampleApplication>()
        assertNotNull(registry)
        assertNotNull(registry.application)
        assertNotNull(registry.globalApplicationContext)
    }
}