package dev.tonholo.awesomeapp.feature.onboard.di

import dagger.Component
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureComponent
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureEntryPoint

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
