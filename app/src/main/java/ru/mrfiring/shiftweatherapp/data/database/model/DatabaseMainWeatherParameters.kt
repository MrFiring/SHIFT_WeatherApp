package ru.mrfiring.shiftweatherapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseMainWeatherParameters(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Double,
    val pressureAtSeaLevel: Int,
    val pressureAtGroundLevel: Int
)