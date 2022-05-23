package dev.tonholo.awesomeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.Destinations

@Module
@InstallIn(SingletonComponent::class)
object AppNavigationModule {
    @[Provides IntoSet]
    fun provideFeatureDestination(): Destination = Destinations.Feed
}
