package dev.tonholo.awesomeapp.di.dfm

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.tonholo.awesomeapp.navigation.DestinationManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DynamicFeatureModule {
    @[Provides Singleton]
    fun provideDynamicFeatureLoaderManager(
        dynamicFeatureLoaders: Map<String, @JvmSuppressWildcards DynamicFeatureLoader>,
        destinationManager: DestinationManager,
        @ApplicationContext context: Context,
    ): DynamicFeatureLoaderManager = DynamicFeatureLoaderManagerImpl(
        dynamicFeatureLoaders, destinationManager, context
    )
}
