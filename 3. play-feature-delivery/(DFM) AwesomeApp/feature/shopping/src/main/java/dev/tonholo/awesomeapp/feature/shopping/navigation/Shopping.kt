package dev.tonholo.awesomeapp.feature.shopping.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.tonholo.awesomeapp.feature.shopping.ShoppingScreen
import dev.tonholo.awesomeapp.feature.shopping.ShoppingViewModel
import dev.tonholo.awesomeapp.navigation.Destination
import dev.tonholo.awesomeapp.navigation.DestinationRoute
import dev.tonholo.awesomeapp.navigation.Routes
import javax.inject.Provider

class Shopping(
    private val shoppingViewModelProvider: Provider<ShoppingViewModel>,
) : Destination, DestinationRoute by Routes.Shopping {

    override fun setup(navController: NavController, builder: NavGraphBuilder) = with(builder) {
        composable(this@Shopping.route) {
            val viewModel = remember { shoppingViewModelProvider.get() }
            ShoppingScreen(navController = navController, viewModel = viewModel)
        }
    }
}
