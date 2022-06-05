package dev.tonholo.awesomeapp.feature.shopping.di

import dagger.Component
import dev.tonholo.awesomeapp.di.AppModule
import dev.tonholo.awesomeapp.di.DynamicFeatureComponent
import dev.tonholo.awesomeapp.di.DynamicFeatureEntryPoint

@Component(
    dependencies = [DynamicFeatureEntryPoint::class],
    modules = [
        AppModule::class,
        ShoppingNavigationModule::class,
    ],
)
interface ShoppingComponent : DynamicFeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(dfmEntryPoint: DynamicFeatureEntryPoint): ShoppingComponent
    }
}
