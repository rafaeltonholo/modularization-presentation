package dev.tonholo.awesomeapp.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

interface DispatcherProvider: CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> get() = Key

    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

    companion object Key : CoroutineContext.Key<DispatcherProvider>
}

class DefaultDispatcherProvider : DispatcherProvider {

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val main: CoroutineDispatcher get() = Dispatchers.Main

    override val mainImmediate: CoroutineDispatcher get() = Dispatchers.Main.immediate

    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
