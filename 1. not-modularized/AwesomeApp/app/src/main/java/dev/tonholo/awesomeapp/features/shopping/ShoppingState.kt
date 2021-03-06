package dev.tonholo.awesomeapp.features.shopping

import dev.tonholo.awesomeapp.data.model.UnsplashImage

data class ShoppingState(
    val isSearching: Boolean = false,
    val waitingFirstInput: Boolean = true,
    val images: List<UnsplashImage> = emptyList(),
    val filter: String = "",
)
