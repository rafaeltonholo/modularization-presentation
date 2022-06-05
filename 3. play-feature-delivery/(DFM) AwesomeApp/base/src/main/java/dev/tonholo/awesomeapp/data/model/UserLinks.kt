package dev.tonholo.awesomeapp.data.model


import com.google.gson.annotations.SerializedName

data class UserLinks(
    @SerializedName("html")
    val html: String,
    @SerializedName("likes")
    val likes: String,
    @SerializedName("photos")
    val photos: String,
    @SerializedName("portfolio")
    val portfolio: String,
    @SerializedName("self")
    val self: String,
)
