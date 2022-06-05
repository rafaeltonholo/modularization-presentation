package dev.tonholo.awesomeapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import dev.tonholo.awesomeapp.data.model.UnsplashImage

data class SearchDto(
    val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<UnsplashImage>,
)
