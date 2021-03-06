package dev.tonholo.awesomeapp.feature.feed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.feed.FeedScreen
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationRoute
import dev.tonholo.awesomeapp.navigation.Routes

object Feed : Destination, DestinationRoute by Routes.Feed {
    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(Feed.route) {
            FeedScreen(navController)
        }
    }
}
