package ru.mrfiring.shiftweatherapp.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coord: Coordinates
)