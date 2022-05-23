package dev.tonholo.awesomeapp.data.model


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("position")
    val position: Position?,
)
