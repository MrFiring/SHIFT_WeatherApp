package ru.mrfiring.shiftweatherapp.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double?
)