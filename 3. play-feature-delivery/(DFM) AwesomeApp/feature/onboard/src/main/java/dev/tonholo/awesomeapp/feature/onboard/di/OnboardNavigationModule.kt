package dev.tonholo.awesomeapp.feature.onboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.feature.onboard.OnboardViewModel
import dev.tonholo.awesomeapp.feature.onboard.navigation.Onboard
import dev.tonholo.awesomeapp.navigation.Destination
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
object OnboardNavigationModule {

    @[Provides IntoSet]
    fun provideFeatureDestination(
        provider: Provider<OnboardViewModel>,
    ): Destination = Onboard(provider)
}
