package dev.tonholo.awesomeapp.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationManager
import dev.tonholo.awesomeapp.navigation.DestinationManagerImpl
import dev.tonholo.awesomeapp.navigation.StartDestination

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideDestinationManager(
        @StartDestination startDestination: String,
        featuresDestinations: Set<@JvmSuppressWildcards Destination>,
    ): DestinationManager = DestinationManagerImpl(startDestination, featuresDestinations)
}
