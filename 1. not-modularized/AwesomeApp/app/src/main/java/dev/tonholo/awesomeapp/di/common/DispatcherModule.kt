package dev.tonholo.awesomeapp.di.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tonholo.awesomeapp.common.DefaultDispatcherProvider
import dev.tonholo.awesomeapp.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Singleton
    fun providesDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher =
        dispatcherProvider.default

    @Provides
    @IODispatcher
    fun providesIODispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher =
        dispatcherProvider.io

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher =
        dispatcherProvider.main

    @Provides
    @MainImmediateDispatcher
    fun providesMainImmediateDispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher =
        dispatcherProvider.mainImmediate

    @Provides
    @UnconfinedDispatcher
    fun providesUnconfinedDispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher =
        dispatcherProvider.unconfined
}
