package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "1h") val forLastOneHour: Double,
    @Json(name = "3h") val forLastThreeHours: Double?
)