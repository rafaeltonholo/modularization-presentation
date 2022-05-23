package dev.tonholo.awesomeapp.feature.feed.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.feature.feed.navigation.Feed
import dev.tonholo.awesomeapp.navigation.Destination

@Module
@InstallIn(SingletonComponent::class)
object FeedNavigationModule {
    @[Provides IntoSet]
    fun provideFeatureDestination(): Destination = Feed
}
