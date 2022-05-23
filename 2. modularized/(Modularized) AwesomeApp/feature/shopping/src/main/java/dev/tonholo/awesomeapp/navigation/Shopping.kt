package dev.tonholo.awesomeapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.shopping.ShoppingScreen

object Shopping : Destination, DestinationRoute by Routes.Shopping {

    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(Shopping.route) {
            ShoppingScreen(navController = navController)
        }
    }
}
