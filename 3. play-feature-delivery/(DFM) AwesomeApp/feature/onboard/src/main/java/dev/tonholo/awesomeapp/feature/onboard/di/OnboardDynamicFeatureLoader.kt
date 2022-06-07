package dev.tonholo.awesomeapp.feature.onboard.di

import androidx.annotation.Keep
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureEntryPoint
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureLoader

@Keep
class OnboardDynamicFeatureLoader : DynamicFeatureLoader {
    override fun load(dfmEntryPoint: DynamicFeatureEntryPoint): OnboardComponent =
        DaggerOnboardComponent.factory().create(dfmEntryPoint)
}
