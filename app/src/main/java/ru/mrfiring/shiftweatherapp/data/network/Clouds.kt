package ru.mrfiring.shiftweatherapp.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clouds(
    val all: Double
)