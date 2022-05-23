package dev.tonholo.awesomeapp.features.imagedetail

import dev.tonholo.awesomeapp.data.model.UnsplashImage

data class ImageDetailState(
    val isLoading: Boolean = false,
    val image: UnsplashImage? = null,
    val errorMessage: String? = null,
)
