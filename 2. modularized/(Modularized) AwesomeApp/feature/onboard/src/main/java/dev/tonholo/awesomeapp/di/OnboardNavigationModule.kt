package dev.tonholo.awesomeapp.di

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.tonholo.awesomeapp.feature.onboard.OnboardScreen
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.StartDestination


internal object Onboard : Destination {
    override val route = "onboard"

    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(Onboard.route) {
            OnboardScreen(navController)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object OnboardNavigationModule {

    @[Provides IntoSet]
    fun provideFeatureDestination(): Destination = Onboard

    @[Provides StartDestination]
    fun provideStartDestination(): String = Onboard.route
}
