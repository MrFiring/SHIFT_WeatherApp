package ru.mrfiring.shiftweatherapp.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseWeatherContainer(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val base: String,
    val lastUpdate: Long,
    val timeZone: Long,
    val clouds: Double,
    @Embedded(prefix = "weather")
    val weather: DatabaseWeather,
    @Embedded(prefix = "main")
    val mainParams: DatabaseMainWeatherParameters,
    @Embedded(prefix = "wind")
    val wind: DatabaseWind,
    @Embedded(prefix = "rain")
    val rain: DatabaseRain,
    @Embedded(prefix = "snow")
    val snow: DatabaseSnow

)