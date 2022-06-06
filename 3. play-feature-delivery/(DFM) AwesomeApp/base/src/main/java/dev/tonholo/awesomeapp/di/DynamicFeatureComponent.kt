package dev.tonholo.awesomeapp.di

import dev.tonholo.awesomeapp.navigation.Destination

interface DynamicFeatureComponent {
    fun getFeatureRoutes(): Set<@JvmSuppressWildcards Destination>
}

interface DynamicFeatureLoader {
    fun load(dfmEntryPoint: DynamicFeatureEntryPoint): DynamicFeatureComponent
}

object NotInstalledDynamicFeatureComponent : DynamicFeatureComponent {
    override fun getFeatureRoutes(): Set<Destination> = emptySet()
}

object NotInstalledDynamicFeatureLoader : DynamicFeatureLoader {
    override fun load(dfmEntryPoint: DynamicFeatureEntryPoint): DynamicFeatureComponent =
        NotInstalledDynamicFeatureComponent
}
