package dev.tonholo.awesomeapp.di.common

import javax.inject.Qualifier

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class IODispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class MainImmediateDispatcher

@Qualifier
annotation class UnconfinedDispatcher
