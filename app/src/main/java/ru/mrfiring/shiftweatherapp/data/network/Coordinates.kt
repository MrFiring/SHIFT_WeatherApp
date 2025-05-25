package ru.mrfiring.shiftweatherapp.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lon") val longitude: Double,
    @Json(name = "lat") val latitude: Double
)