package dev.tonholo.awesomeapp.di.dfm.fake

import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureLoaderManager
import dev.tonholo.awesomeapp.navigation.DestinationManager
import kotlinx.coroutines.flow.SharedFlow

object FakeDynamicFeatureLoaderManager : DynamicFeatureLoaderManager {
    override val destinationManager: DestinationManager
        get() = throw NotImplementedError()
    override val reloadEvents: SharedFlow<String>
        get() = throw NotImplementedError()

    override fun earlyInit() {
        throw NotImplementedError()
    }

    override suspend fun requestReloadDynamicFeatureModule(moduleName: String) {
        throw NotImplementedError()
    }
}
