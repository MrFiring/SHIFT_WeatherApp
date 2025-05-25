package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.network.model.City
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

fun DatabaseCity.asDomainObject(): DomainCity = DomainCity(
    id,
    name,
    state,
    country,
    longitude,
    latitude,
    favorite
)

fun City.asDatabaseObject(): DatabaseCity = DatabaseCity(
    id,
    name,
    state,
    country,
    coord.longitude,
    coord.latitude
)

fun DomainCity.asDatabaseObject(): DatabaseCity = DatabaseCity(
    id,
    name,
    state,
    country,
    longitude,
    latitude,
    favorite
)