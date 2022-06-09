package dev.tonholo.awesomeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun Navigation(
    navController: NavHostController,
    destinationManager: DestinationManager,
) {
    NavHost(
        navController = navController,
        startDestination = destinationManager.startDestination,
    ) {
        with(destinationManager) {
            setupRoutes(navController)
        }
    }
}
