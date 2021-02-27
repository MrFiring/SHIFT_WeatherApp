package ru.mrfiring.shiftweatherapp.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseCity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val longitude: Double,
    val latitude: Double
)

@Entity
data class DatabaseWeatherContainer(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val base: String,
    val lastUpdate: Long,
    val timeZone: Long,
    val clouds: Double
)

@Entity
data class DatabaseWeather(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)

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

@Entity
data class DatabaseWind(
    @PrimaryKey(autoGenerate = false)
    val id:Long,
    val speed: Double,
    val deg: Double,
    val gust: Double
)

@Entity
data class DatabaseRain(
    @PrimaryKey
    val id: Long,
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)

@Entity
data class DatabaseSnow(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)

