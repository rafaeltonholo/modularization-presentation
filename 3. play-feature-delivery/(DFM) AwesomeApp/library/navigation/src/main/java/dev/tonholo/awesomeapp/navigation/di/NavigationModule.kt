package dev.tonholo.awesomeapp.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tonholo.awesomeapp.navigation.*
import dev.tonholo.awesomeapp.navigation.DestinationManagerImpl

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideDestinationManager(
        @StartDestination startDestination: String,
        featuresDestinations: Set<@JvmSuppressWildcards Destination>,
    ): DestinationManager = DestinationManagerImpl(startDestination, featuresDestinations)

    @[Provides StartDestination]
    fun provideStartDestination(): String = Routes.Onboard.route
}
