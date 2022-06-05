package dev.tonholo.awesomeapp.feature.onboard.di

import dagger.Component
import dev.tonholo.awesomeapp.di.DynamicFeatureComponent
import dev.tonholo.awesomeapp.di.DynamicFeatureEntryPoint

@Component(
    dependencies = [DynamicFeatureEntryPoint::class],
    modules = [
        OnboardNavigationModule::class,
    ],
)
interface OnboardComponent : DynamicFeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(dfmEntryPoint: DynamicFeatureEntryPoint): OnboardComponent
    }
}
