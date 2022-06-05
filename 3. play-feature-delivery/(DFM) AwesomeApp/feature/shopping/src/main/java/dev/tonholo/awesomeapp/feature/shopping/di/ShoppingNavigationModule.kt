package dev.tonholo.awesomeapp.feature.shopping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.feature.shopping.ShoppingViewModel
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.feature.shopping.navigation.Shopping
import javax.inject.Provider

@Module
@InstallIn(SingletonComponent::class)
object ShoppingNavigationModule {
    @[Provides IntoSet]
    fun provideFeatureDestination(provider: Provider<ShoppingViewModel>): Destination =
        Shopping(provider)
}
