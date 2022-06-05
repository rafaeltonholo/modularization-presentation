package dev.tonholo.awesomeapp.navigation

object Routes {
    object Onboard : DestinationRoute {
        override val route = "onboard"
    }

    object Feed : DestinationRoute {
        override val route = "feed"
    }

    object Camera : DestinationRoute {
        override val route = "camera"
    }

    object ImageDetails : DestinationRoute {
        const val PARAM_ID = "id"
        private const val baseRoute = "image_detail"
        override val route = "$baseRoute/{$PARAM_ID}"

        operator fun invoke(id: String) = "$baseRoute/$id"
    }

    object Shopping : DestinationRoute {
        override val route = "shopping"
    }
}
