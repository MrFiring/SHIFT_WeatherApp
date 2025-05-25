package ru.mrfiring.shiftweatherapp.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SystemInfo(
    val type: Int?,
    val id: Int?,
    val message: Double?,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)