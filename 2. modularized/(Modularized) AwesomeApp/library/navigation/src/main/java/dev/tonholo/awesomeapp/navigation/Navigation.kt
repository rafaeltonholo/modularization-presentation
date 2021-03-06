package dev.tonholo.awesomeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    destinationManager: DestinationManager,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destinationManager.startDestination,
    ) {
        with(destinationManager) {
            setupRoutes(navController)
        }
    }
}
