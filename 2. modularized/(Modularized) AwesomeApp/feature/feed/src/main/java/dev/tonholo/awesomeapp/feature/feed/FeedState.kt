package dev.tonholo.awesomeapp.feature.feed

import dev.tonholo.awesomeapp.data.model.UnsplashImage

data class FeedState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val images: List<UnsplashImage> = emptyList(),
    val destinationTarget: String? = null,
)
