package dev.tonholo.awesomeapp.feature.shopping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.feature.shopping.navigation.Shopping

@Module
@InstallIn(SingletonComponent::class)
object ShoppingNavigationModule {
    @[Provides IntoSet]
    fun provideFeatureDestination(): Destination = Shopping
}
