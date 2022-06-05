package dev.tonholo.awesomeapp.feature.shopping.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.shopping.ShoppingScreen
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationRoute
import dev.tonholo.awesomeapp.navigation.Routes

object Shopping : Destination, DestinationRoute by Routes.Shopping {

    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(Shopping.route) {
            ShoppingScreen(navController = navController)
        }
    }
}
