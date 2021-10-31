package io.kio.android.codebase.core

object PluginRegistry {
    private val plugins = mutableListOf<CodeBasePlugin>()

    internal fun notifyPlugins() {
        plugins.forEach { it.apply() }
    }

    fun registerPlugin(plugin: CodeBasePlugin) {
        plugins += plugin
    }
}

interface CodeBasePlugin {
    fun apply()
}