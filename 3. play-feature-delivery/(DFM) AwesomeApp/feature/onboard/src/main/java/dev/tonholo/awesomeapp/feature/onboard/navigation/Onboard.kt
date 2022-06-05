package dev.tonholo.awesomeapp.feature.onboard.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.onboard.OnboardScreen
import dev.tonholo.awesomeapp.feature.onboard.OnboardViewModel
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationRoute
import dev.tonholo.awesomeapp.navigation.Routes
import javax.inject.Provider

internal class Onboard(
    private val onboardViewModelProvider: Provider<OnboardViewModel>,
) : Destination, DestinationRoute by Routes.Onboard {
    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(this@Onboard.route) {
            val viewModel = remember { onboardViewModelProvider.get() }
            OnboardScreen(navController, viewModel)
        }
    }
}
