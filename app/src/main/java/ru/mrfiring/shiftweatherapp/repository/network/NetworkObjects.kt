package ru.mrfiring.shiftweatherapp.repository.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.mrfiring.shiftweatherapp.repository.database.*

@JsonClass(generateAdapter = true)
data class City(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val coord: Coordinates
)

@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lon") val longitude: Double,
    @Json(name = "lat") val latitude: Double
)

@JsonClass(generateAdapter = true)
data class WeatherContainer(
    val coord: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: MainWeatherParameters,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain?,
    val snow: Snow?,
    val dt: Long,
    val sys: SystemInfo,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Int
)


@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@JsonClass(generateAdapter = true)
data class MainWeatherParameters(
    val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Double,
    @Json(name= "sea_level") val pressureAtSeaLevel: Int?,
    @Json(name= "grnd_level") val pressureAtGroundLevel: Int?,
)

@JsonClass(generateAdapter = true)
data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double?
)

@JsonClass(generateAdapter = true)
data class Clouds(
    val all: Double
)

@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "1h") val forLastOneHour: Double,
    @Json(name = "3h") val forLastThreeHours: Double?
)

@JsonClass(generateAdapter = true)
data class Snow(
    @Json(name = "1h") val forLastOneHour: Double,
    @Json(name = "3h") val forLastThreeHours: Double?
)

@JsonClass(generateAdapter = true)
data class SystemInfo(
    val type: Int,
    val id: Int,
    val message: Double?,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

fun City.asDatabaseObject() : DatabaseCity = DatabaseCity(
    id,
    name,
    state,
    country,
    coord.longitude,
    coord.latitude
)

fun WeatherContainer.asDatabaseObject(clouds: Double): DatabaseWeatherContainer = DatabaseWeatherContainer(
    id,
    name,
    base,
    dt,
    timezone,
    clouds
)

fun Weather.asDatabaseObject(ownerId: Long): DatabaseWeather = DatabaseWeather(
    ownerId,
    id,
    main,
    description,
    icon
)

fun MainWeatherParameters.asDatabaseObject(id: Long): DatabaseMainWeatherParameters = DatabaseMainWeatherParameters(
    id,
    temp,
    feelsLike,
    tempMin,
    tempMax,
    pressure,
    humidity,
    pressureAtSeaLevel ?: -1,
    pressureAtGroundLevel ?: -1,
)

fun Wind.asDatabaseObject(id: Long): DatabaseWind = DatabaseWind(
    id,
    speed,
    deg,
    gust ?: -1.0
)

fun Rain.asDatabaseObject(id: Long): DatabaseRain = DatabaseRain(
    id,
    forLastOneHour,
    forLastThreeHours ?: -1.0
)

fun Snow.asDatabaseObject(id: Long): DatabaseSnow = DatabaseSnow(
    id,
    forLastOneHour,
    forLastThreeHours ?: -1.0
)