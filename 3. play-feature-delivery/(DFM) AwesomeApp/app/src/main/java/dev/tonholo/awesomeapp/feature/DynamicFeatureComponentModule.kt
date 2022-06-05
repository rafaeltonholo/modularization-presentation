package dev.tonholo.awesomeapp.feature

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.di.DynamicFeatureLoader

@Module
@InstallIn(SingletonComponent::class)
object DynamicFeatureComponentModule {
    @[Provides IntoSet]
    fun provideShoppingDynamicFeatureLoader(): DynamicFeatureLoader =
        Class.forName("dev.tonholo.awesomeapp.feature.shopping.di.DefaultShoppingDynamicFeatureLoader")
            .newInstance() as DynamicFeatureLoader
}
