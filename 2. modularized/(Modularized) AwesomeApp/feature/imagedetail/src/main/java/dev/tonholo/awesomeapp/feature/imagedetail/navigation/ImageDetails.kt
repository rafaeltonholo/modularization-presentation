package dev.tonholo.awesomeapp.feature.imagedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.tonholo.awesomeapp.feature.imagedetail.ImageDetailScreen
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationRoute
import dev.tonholo.awesomeapp.navigation.Routes
import dev.tonholo.awesomeapp.navigation.Routes.ImageDetails.PARAM_ID

object ImageDetails : Destination, DestinationRoute by Routes.ImageDetails {

    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(
            route = ImageDetails.route,
            arguments = listOf(
                navArgument(PARAM_ID) {
                    type = NavType.StringType
                }
            ),
        ) {
            val id = it.arguments?.getString(PARAM_ID)
                ?: throw IllegalArgumentException("Can't access image detail without id")
            ImageDetailScreen(imageId = id, navController = navController)
        }
    }
}
