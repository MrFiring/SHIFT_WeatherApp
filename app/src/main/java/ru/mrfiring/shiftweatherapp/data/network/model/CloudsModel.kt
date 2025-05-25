package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CloudsModel(
    val all: Double
)