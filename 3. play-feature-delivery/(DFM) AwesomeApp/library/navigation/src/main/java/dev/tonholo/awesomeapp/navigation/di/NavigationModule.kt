package dev.tonholo.awesomeapp.navigation.di

import com.google.android.play.core.splitinstall.SplitInstallManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tonholo.awesomeapp.navigation.*

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideDestinationManager(
        @StartDestination startDestination: String,
        featuresDestinations: Set<@JvmSuppressWildcards Destination>,
    ): DestinationManager = DestinationManagerImpl(startDestination, featuresDestinations)

    @[Provides StartDestination]
    fun provideStartDestination(splitInstallManager: SplitInstallManager): String =
        if (splitInstallManager.installedModules.any { it == Routes.Onboard.route })
            Routes.Onboard.route
        else
            Routes.Feed.route
}
