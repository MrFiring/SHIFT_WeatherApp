package ru.mrfiring.shiftweatherapp.repository.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coord: Coordinates
)

@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lon") val longitude: Double,
    @Json(name = "lat") val latitude: Double
)