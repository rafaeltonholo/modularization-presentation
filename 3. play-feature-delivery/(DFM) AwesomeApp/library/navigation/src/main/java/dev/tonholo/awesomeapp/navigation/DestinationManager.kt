package dev.tonholo.awesomeapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import javax.inject.Qualifier

interface DestinationRoute {
    val route: String
    operator fun invoke() = route
}

interface Destination : DestinationRoute {

    fun setup(navController: NavController, builder: NavGraphBuilder)
}

interface DestinationManager {
    val startDestination: String
    val featuresDestinations: Set<Destination>

    fun NavGraphBuilder.setupRoutes(navController: NavController) {
        featuresDestinations.forEach { it.setup(navController, this) }
    }

    fun attachDynamicFeatureModuleDestinations(destinations: Set<Destination>)
}

internal class DestinationManagerImpl(
    override val startDestination: String,
    featuresDestinations: Set<Destination>,
) : DestinationManager {
    private val destinations = featuresDestinations.toMutableSet()

    override val featuresDestinations: Set<Destination>
        get() = destinations

    override fun attachDynamicFeatureModuleDestinations(destinations: Set<Destination>) {
        this.destinations.addAll(destinations)
    }
}

@Qualifier
annotation class StartDestination
