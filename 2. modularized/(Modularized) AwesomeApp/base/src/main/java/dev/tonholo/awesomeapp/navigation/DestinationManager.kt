package dev.tonholo.awesomeapp.navigationimport androidx.navigation.NavControllerimport androidx.navigation.NavGraphBuilderimport javax.inject.Qualifierinterface Destination {    val route: String    fun setup(navController: NavController, builder: NavGraphBuilder)    operator fun invoke() = route}interface DestinationManager {    val startDestination: String    val featuresDestinations: Set<Destination>    fun NavGraphBuilder.setupRoutes(navController: NavController) {        featuresDestinations.forEach { it.setup(navController, this) }    }}internal class DestinationManagerImpl(    override val startDestination: String,    override val featuresDestinations: Set<Destination>,) : DestinationManager@Qualifierannotation class StartDestination