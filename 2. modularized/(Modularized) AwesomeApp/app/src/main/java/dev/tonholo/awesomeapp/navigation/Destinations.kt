package dev.tonholo.awesomeapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.feed.FeedScreen

object Destinations {

    object Feed : Destination {
        override val route = "feed"

        override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
            composable(Feed.route) {
                dev.tonholo.awesomeapp.feature.feed.FeedScreen(navController)
            }
        }
    }
}
