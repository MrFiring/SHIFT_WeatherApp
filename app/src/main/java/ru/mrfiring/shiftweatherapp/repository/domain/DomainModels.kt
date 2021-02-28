package ru.mrfiring.shiftweatherapp.repository.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mrfiring.shiftweatherapp.repository.database.*


data class DomainCity(
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val longitude: Double,
    val latitude: Double
)

data class DomainWeatherContainer(
    val lastUpdate: Long,
    val timeZone: Long,
    val clouds: Double,
    val weather: DomainWeather,
    val mainParams: DomainMainWeatherParameters,
)

data class DomainWeather(
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class DomainMainWeatherParameters(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Double,
    val pressureAtSeaLevel: Int,
    val pressureAtGroundLevel: Int
)

data class DomainWind(
    val speed: Double,
    val deg: Double,
    val gust: Double
)


data class DomainRain(
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)


data class DomainSnow(
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)

fun DatabaseCity.asDomainObject(): DomainCity = DomainCity(
    id, name, state, country, longitude, latitude
)

fun DatabaseWeatherContainer.asDomainObject(
    weather: DatabaseWeather,
    params: DatabaseMainWeatherParameters
): DomainWeatherContainer = DomainWeatherContainer(
    lastUpdate,
    timeZone,
    clouds,
    weather.asDomainObject(),
    params.asDomainObject()
)

fun DatabaseWeather.asDomainObject(): DomainWeather = DomainWeather(
    weatherId, main, description, icon
)

fun DatabaseMainWeatherParameters.asDomainObject(): DomainMainWeatherParameters = DomainMainWeatherParameters(
    temp, feelsLike, tempMin, tempMax, pressure, humidity, pressureAtSeaLevel, pressureAtGroundLevel
)

fun DatabaseWind.asDomainObject(): DomainWind = DomainWind(
   speed, deg, gust
)

fun DatabaseRain.asDomainObject(): DomainRain = DomainRain(
    forLastOneHour, forLastThreeHours
)

fun DatabaseSnow.asDomainObject(): DomainSnow = DomainSnow(
    forLastOneHour, forLastThreeHours
)

