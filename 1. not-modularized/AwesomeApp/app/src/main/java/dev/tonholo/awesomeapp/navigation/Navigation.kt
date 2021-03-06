package dev.tonholo.awesomeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.tonholo.awesomeapp.navigation.Destinations.setupRoutes

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.startDestination,
    ) {
        setupRoutes(navController)
    }
}
