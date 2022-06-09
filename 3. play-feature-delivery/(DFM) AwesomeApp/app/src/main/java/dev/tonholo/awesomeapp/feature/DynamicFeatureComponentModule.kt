package dev.tonholo.awesomeapp.feature

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureLoader
import dev.tonholo.awesomeapp.di.dfm.NotInstalledDynamicFeatureLoader

private const val TAG = "DynamicFeatureComponent"

@Module
@InstallIn(SingletonComponent::class)
object DynamicFeatureComponentModule {
    @[Provides IntoMap StringKey("shopping")]
    fun provideShoppingDynamicFeatureLoader(): DynamicFeatureLoader = try {
        Class.forName("dev.tonholo.awesomeapp.feature.shopping.di.DefaultShoppingDynamicFeatureLoader")
            .newInstance() as DynamicFeatureLoader
    } catch (e: ClassNotFoundException) {
        Log.w(TAG, "shopping: module not installed", e)
        NotInstalledDynamicFeatureLoader
    }

    @[Provides IntoMap StringKey("onboard")]
    fun provideOnboardDynamicFeatureLoader(): DynamicFeatureLoader = try {
        Class.forName("dev.tonholo.awesomeapp.feature.onboard.di.OnboardDynamicFeatureLoader")
            .newInstance() as DynamicFeatureLoader
    } catch (e: ClassNotFoundException) {
        Log.w(TAG, "onboard: module not installed", e)
        NotInstalledDynamicFeatureLoader
    }
}
