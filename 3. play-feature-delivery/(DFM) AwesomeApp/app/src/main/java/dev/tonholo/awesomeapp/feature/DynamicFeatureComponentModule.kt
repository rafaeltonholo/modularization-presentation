package dev.tonholo.awesomeapp.feature

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.di.DynamicFeatureLoader
import dev.tonholo.awesomeapp.di.NotInstalledDynamicFeatureLoader
import javax.annotation.Nullable

private const val TAG = "DynamicFeatureComponent"

@Module
@InstallIn(SingletonComponent::class)
object DynamicFeatureComponentModule {
    @[Provides IntoSet]
    fun provideShoppingDynamicFeatureLoader(): DynamicFeatureLoader = try {
        Class.forName("dev.tonholo.awesomeapp.feature.shopping.di.DefaultShoppingDynamicFeatureLoader")
            .newInstance() as DynamicFeatureLoader
    } catch (e: ClassNotFoundException) {
        Log.w(TAG, "provideShoppingDynamicFeatureLoader: module not installed", e)
        NotInstalledDynamicFeatureLoader
    }

    @[Provides IntoSet]
    fun provideOnboardDynamicFeatureLoader(): DynamicFeatureLoader = try {
        Class.forName("dev.tonholo.awesomeapp.feature.onboard.di.OnboardDynamicFeatureLoader")
            .newInstance() as DynamicFeatureLoader
    } catch (e: ClassNotFoundException) {
        Log.w(TAG, "provideOnboardDynamicFeatureLoader: module not installed", e)
        NotInstalledDynamicFeatureLoader
    }
}
