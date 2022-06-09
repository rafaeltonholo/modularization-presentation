package dev.tonholo.awesomeapp.di.dfm

import android.content.Context
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.tonholo.awesomeapp.navigation.DestinationManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface DynamicFeatureLoaderManager {
    val destinationManager: DestinationManager
    val reloadEvents: SharedFlow<String>
    fun earlyInit()
    suspend fun requestReloadDynamicFeatureModule(moduleName: String)
}

internal class DynamicFeatureLoaderManagerImpl(
    dynamicFeatureLoaders: Map<String, @JvmSuppressWildcards DynamicFeatureLoader>,
    override val destinationManager: DestinationManager,
    @ApplicationContext private val context: Context,
) : DynamicFeatureLoaderManager {
    private val _dynamicFeatureLoaders = dynamicFeatureLoaders.toMutableMap()
    private val _reloadEvents = MutableSharedFlow<String>()
    override val reloadEvents: SharedFlow<String>
        get() = _reloadEvents.asSharedFlow()


    override fun earlyInit() {
        val dynamicFeatureComponents = _dynamicFeatureLoaders.values.map { dfm ->
            dfm.load(EntryPointAccessors.fromApplication(context))
        }.toSet()

        destinationManager.attachDynamicFeatureModuleDestinations(
            dynamicFeatureComponents.flatMap { dfm -> dfm.getFeatureRoutes() }.toSet()
        )
    }

    override suspend fun requestReloadDynamicFeatureModule(moduleName: String) {
        if (!_dynamicFeatureLoaders.containsKey(moduleName)) {
            return
        }

        _reloadEvents.emit(moduleName)
    }
}
