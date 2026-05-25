package ru.mrfiring.shiftweatherapp.domain.models

data class Weather(
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)