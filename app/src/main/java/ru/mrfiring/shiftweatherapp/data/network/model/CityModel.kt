package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityModel(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coord: CoordinatesModel
)