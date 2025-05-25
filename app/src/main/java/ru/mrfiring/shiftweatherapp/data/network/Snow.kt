package ru.mrfiring.shiftweatherapp.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Snow(
    @Json(name = "1h") val forLastOneHour: Double,
    @Json(name = "3h") val forLastThreeHours: Double?
)