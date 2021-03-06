package dev.tonholo.awesomeapp.feature.camera.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.camera.CameraScreen
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationRoute
import dev.tonholo.awesomeapp.navigation.Routes

object Camera : Destination, DestinationRoute by Routes.Camera {
    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(Camera.route) {
            CameraScreen(navController)
        }
    }
}
