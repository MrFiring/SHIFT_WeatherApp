package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.network.model.CityModel
import ru.mrfiring.shiftweatherapp.domain.models.City

fun DatabaseCity.asDomainObject(): City = City(
    id,
    name,
    state,
    country,
    longitude,
    latitude,
    favorite
)

fun CityModel.asDatabaseObject(): DatabaseCity = DatabaseCity(
    id,
    name,
    state,
    country,
    coord.longitude,
    coord.latitude
)

fun City.asDatabaseObject(): DatabaseCity = DatabaseCity(
    id,
    name,
    state,
    country,
    longitude,
    latitude,
    favorite
)