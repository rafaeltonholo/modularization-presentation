package dev.tonholo.awesomeapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.tonholo.awesomeapp.features.camera.CameraScreen
import dev.tonholo.awesomeapp.features.feed.FeedScreen
import dev.tonholo.awesomeapp.features.imagedetail.ImageDetailScreen
import dev.tonholo.awesomeapp.features.onboard.OnboardScreen
import dev.tonholo.awesomeapp.features.shopping.ShoppingScreen

sealed interface Destination {
    val route: String

    fun setup(navController: NavController, builder: NavGraphBuilder)

    operator fun invoke() = route
}

object Destinations {
    val startDestination = Onboard.route

    object Onboard : Destination {
        override val route = "onboard"

        override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
            composable(this@Onboard.route) {
                OnboardScreen(navController)
            }
        }
    }

    object Feed : Destination {
        override val route = "feed"

        override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
            composable(this@Feed.route) {
                FeedScreen(navController)
            }
        }
    }

    object ImageDetails : Destination {
        private const val PARAM_ID = "id"
        private const val baseRoute = "image_detail"
        override val route = "$baseRoute/{$PARAM_ID}"

        override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
            composable(
                route = this@ImageDetails.route,
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

        operator fun invoke(id: String) = "$baseRoute/$id"
    }

    object Shopping : Destination {
        override val route = "shopping"

        override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
            composable(this@Shopping.route) {
                ShoppingScreen(navController = navController)
            }
        }
    }

    object Camera : Destination {
        override val route = "camera"

        override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
            composable(this@Camera.route) {
                CameraScreen(navController)
            }
        }
    }

    fun NavGraphBuilder.setupRoutes(navController: NavController) {
        listOf(
            Onboard,
            Feed,
            ImageDetails,
            Shopping,
            Camera,
        ).forEach { it.setup(navController, this) }
    }
}
