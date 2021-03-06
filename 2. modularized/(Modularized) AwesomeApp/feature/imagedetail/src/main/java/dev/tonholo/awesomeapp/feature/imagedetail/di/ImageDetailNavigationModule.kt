package dev.tonholo.awesomeapp.feature.imagedetail.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.feature.imagedetail.navigation.ImageDetails
import dev.tonholo.awesomeapp.navigation.Destination

@Module
@InstallIn(SingletonComponent::class)
object ImageDetailNavigationModule {
    @[Provides IntoSet]
    fun provideFeatureDestination(): Destination = ImageDetails
}
