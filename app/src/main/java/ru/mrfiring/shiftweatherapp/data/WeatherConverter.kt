package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseMainWeatherParameters
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseRain
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseSnow
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeather
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeatherContainer
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWind
import ru.mrfiring.shiftweatherapp.data.network.model.MainWeatherParametersModel
import ru.mrfiring.shiftweatherapp.data.network.model.RainModel
import ru.mrfiring.shiftweatherapp.data.network.model.SnowModel
import ru.mrfiring.shiftweatherapp.data.network.model.WeatherContainer as NetworkWeatherContainer
import ru.mrfiring.shiftweatherapp.data.network.model.WeatherModel
import ru.mrfiring.shiftweatherapp.data.network.model.WindModel
import ru.mrfiring.shiftweatherapp.domain.models.MainWeatherParameters
import ru.mrfiring.shiftweatherapp.domain.models.Rain
import ru.mrfiring.shiftweatherapp.domain.models.Snow
import ru.mrfiring.shiftweatherapp.domain.models.Weather
import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer
import ru.mrfiring.shiftweatherapp.domain.models.Wind

fun DatabaseWeatherContainer.asDomainObject(): WeatherContainer = WeatherContainer(
    lastUpdate,
    timeZone,
    clouds,
    weather.asDomainObject(),
    mainParams.asDomainObject(),
    wind.asDomainObject(),
    rain?.asDomainObject(),
    snow?.asDomainObject()
)

fun NetworkWeatherContainer.asDatabaseObject(): DatabaseWeatherContainer = DatabaseWeatherContainer(
    id,
    name,
    base,
    dt,
    timezone,
    clouds.all,
    weather[0].asDatabaseObject(id),
    main.asDatabaseObject(id),
    wind.asDatabaseObject(id),
    rain?.asDatabaseObject(id) ?: DatabaseRain(-1, 0.0, 0.0),
    snow?.asDatabaseObject(id) ?: DatabaseSnow(-1, 0.0, 0.0)
)


fun DatabaseWeather.asDomainObject(): Weather = Weather(
    weatherId, main, description, icon
)

fun WeatherModel.asDatabaseObject(ownerId: Long): DatabaseWeather = DatabaseWeather(
    ownerId,
    id,
    main,
    description,
    icon
)


fun DatabaseMainWeatherParameters.asDomainObject(): MainWeatherParameters =
    MainWeatherParameters(
        temp,
        feelsLike,
        tempMin,
        tempMax,
        pressure,
        humidity,
        pressureAtSeaLevel,
        pressureAtGroundLevel
    )

fun MainWeatherParametersModel.asDatabaseObject(id: Long): DatabaseMainWeatherParameters =
    DatabaseMainWeatherParameters(
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


fun DatabaseWind.asDomainObject(): Wind = Wind(
    speed, deg, gust
)

fun WindModel.asDatabaseObject(id: Long): DatabaseWind = DatabaseWind(
    id,
    speed,
    deg,
    gust ?: -1.0
)


fun DatabaseRain.asDomainObject(): Rain = Rain(
    forLastOneHour, forLastThreeHours
)

fun RainModel.asDatabaseObject(id: Long): DatabaseRain = DatabaseRain(
    id,
    forLastOneHour,
    forLastThreeHours ?: -1.0
)


fun DatabaseSnow.asDomainObject(): Snow = Snow(
    forLastOneHour, forLastThreeHours
)

fun SnowModel.asDatabaseObject(id: Long): DatabaseSnow = DatabaseSnow(
    id,
    forLastOneHour,
    forLastThreeHours ?: -1.0
)