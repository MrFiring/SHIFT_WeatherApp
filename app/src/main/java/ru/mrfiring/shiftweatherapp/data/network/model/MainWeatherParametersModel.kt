package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainWeatherParametersModel(
    val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Double,
    @Json(name = "sea_level") val pressureAtSeaLevel: Int?,
    @Json(name = "grnd_level") val pressureAtGroundLevel: Int?,
)