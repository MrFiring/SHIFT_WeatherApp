package ru.mrfiring.shiftweatherapp.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherContainer(
    val coord: CoordinatesModel,
    val weather: List<WeatherModel>,
    val base: String,
    val main: MainWeatherParametersModel,
    val wind: WindModel,
    val clouds: CloudsModel,
    val rain: RainModel?,
    val snow: SnowModel?,
    val dt: Long,
    val sys: SystemInfoModel,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Int
)