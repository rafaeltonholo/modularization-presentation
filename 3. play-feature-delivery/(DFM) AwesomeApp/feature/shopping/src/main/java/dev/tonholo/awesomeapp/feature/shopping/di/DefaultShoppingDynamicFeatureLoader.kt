package dev.tonholo.awesomeapp.feature.shopping.di

import androidx.annotation.Keep
import dev.tonholo.awesomeapp.di.DynamicFeatureEntryPoint
import dev.tonholo.awesomeapp.feature.shopping.ShoppingDynamicFeatureLoader

@Keep
class DefaultShoppingDynamicFeatureLoader : ShoppingDynamicFeatureLoader {
    override fun load(dfmEntryPoint: DynamicFeatureEntryPoint): ShoppingComponent =
        DaggerShoppingComponent.factory().create(dfmEntryPoint)
}
