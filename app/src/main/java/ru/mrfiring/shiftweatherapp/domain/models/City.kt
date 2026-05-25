package ru.mrfiring.shiftweatherapp.domain.models

data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    var favorite: Boolean
)
