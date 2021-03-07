package ru.mrfiring.shiftweatherapp.data

import ru.mrfiring.shiftweatherapp.data.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.network.City
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

fun DatabaseCity.asDomainObject(): DomainCity = DomainCity(
    id, name, state, country, longitude, latitude
)

fun City.asDatabaseObject(): DatabaseCity = DatabaseCity(
    id,
    name,
    state,
    country,
    coord.longitude,
    coord.latitude
)