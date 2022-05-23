package dev.tonholo.awesomeapp.feature.camera.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.feature.camera.navigation.Camera
import dev.tonholo.awesomeapp.navigation.Destination

@Module
@InstallIn(SingletonComponent::class)
object CameraNavigationModule {
    @[Provides IntoSet]
    fun provideFeatureDestination(): Destination = Camera
}
