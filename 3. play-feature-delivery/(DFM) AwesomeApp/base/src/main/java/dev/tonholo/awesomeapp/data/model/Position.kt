package dev.tonholo.awesomeapp.data.model


import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)
