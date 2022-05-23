package dev.tonholo.awesomeapp.data.model


import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("download")
    val download: String,
    @SerializedName("download_location")
    val downloadLocation: String,
    @SerializedName("html")
    val html: String,
    @SerializedName("self")
    val self: String,
)
