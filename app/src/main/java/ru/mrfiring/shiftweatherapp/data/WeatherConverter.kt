package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.data.database.*
import ru.mrfiring.shiftweatherapp.data.network.*
import ru.mrfiring.shiftweatherapp.domain.models.*

fun DatabaseWeatherContainer.asDomainObject(): DomainWeatherContainer = DomainWeatherContainer(
    lastUpdate,
    timeZone,
    clouds,
    weather.asDomainObject(),
    mainParams.asDomainObject(),
    wind.asDomainObject(),
    rain?.asDomainObject(),
    snow?.asDomainObject()
)

fun WeatherContainer.asDatabaseObject(): DatabaseWeatherContainer = DatabaseWeatherContainer(
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


fun DatabaseWeather.asDomainObject(): DomainWeather = DomainWeather(
    weatherId, main, description, icon
)

fun Weather.asDatabaseObject(ownerId: Long): DatabaseWeather = DatabaseWeather(
    ownerId,
    id,
    main,
    description,
    icon
)


fun DatabaseMainWeatherParameters.asDomainObject(): DomainMainWeatherParameters =
    DomainMainWeatherParameters(
        temp,
        feelsLike,
        tempMin,
        tempMax,
        pressure,
        humidity,
        pressureAtSeaLevel,
        pressureAtGroundLevel
    )

fun MainWeatherParameters.asDatabaseObject(id: Long): DatabaseMainWeatherParameters =
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


fun DatabaseWind.asDomainObject(): DomainWind = DomainWind(
    speed, deg, gust
)

fun Wind.asDatabaseObject(id: Long): DatabaseWind = DatabaseWind(
    id,
    speed,
    deg,
    gust ?: -1.0
)


fun DatabaseRain.asDomainObject(): DomainRain = DomainRain(
    forLastOneHour, forLastThreeHours
)

fun Rain.asDatabaseObject(id: Long): DatabaseRain = DatabaseRain(
    id,
    forLastOneHour,
    forLastThreeHours ?: -1.0
)


fun DatabaseSnow.asDomainObject(): DomainSnow = DomainSnow(
    forLastOneHour, forLastThreeHours
)

fun Snow.asDatabaseObject(id: Long): DatabaseSnow = DatabaseSnow(
    id,
    forLastOneHour,
    forLastThreeHours ?: -1.0
)