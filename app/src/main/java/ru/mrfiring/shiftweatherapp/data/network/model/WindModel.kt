package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindModel(
    val speed: Double,
    val deg: Double,
    val gust: Double?
)